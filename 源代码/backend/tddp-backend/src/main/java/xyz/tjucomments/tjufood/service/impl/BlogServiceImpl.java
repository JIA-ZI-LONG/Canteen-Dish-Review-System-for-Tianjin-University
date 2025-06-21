// src/main/java/xyz/tjucomments/tjufood/service/impl/BlogServiceImpl.java
package xyz.tjucomments.tjufood.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.BlogDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.UserDTO;
import xyz.tjucomments.tjufood.entity.Blog;
import xyz.tjucomments.tjufood.entity.Notification;
import xyz.tjucomments.tjufood.entity.User;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.mapper.BlogMapper;
import xyz.tjucomments.tjufood.service.IBlogService;
import xyz.tjucomments.tjufood.service.IIncentiveService;
import xyz.tjucomments.tjufood.service.INotificationService;
import xyz.tjucomments.tjufood.service.IUserService;
import xyz.tjucomments.tjufood.utils.constants.IdPrefixConstants;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;
import xyz.tjucomments.tjufood.utils.constants.SystemConstants;
import xyz.tjucomments.tjufood.utils.id.RedisIdWorker;
import xyz.tjucomments.tjufood.utils.security.XssUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserService userService;
    @Resource
    private INotificationService notificationService;
    @Resource
    private IIncentiveService incentiveService;
    @Resource
    private ExecutorService cacheRebuildExecutor;


    @Override
    @Transactional
    public Result createBlog(BlogDTO blogDTO) {
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("请先登录！");
        }

        // XSS防护：对标题进行检查，但允许内容包含安全的HTML标签
        if (XssUtils.containsXss(blogDTO.getTitle())) {
            return Result.fail("标题包含非法字符，请检查后重新提交！");
        }

        Blog blog = BeanUtil.copyProperties(blogDTO, Blog.class);
        // 对标题进行HTML转义，但保留内容的HTML格式（富文本编辑器生成的安全HTML）
        blog.setTitle(XssUtils.escapeHtml(blog.getTitle()));
        // 对富文本内容进行安全过滤，保留允许的HTML标签
        blog.setContent(sanitizeRichTextContent(blog.getContent()));

        blog.setId(redisIdWorker.nextId(IdPrefixConstants.BLOG_ID_PREFIX));
        blog.setUserId(user.getId());

        // 【修复】设置博客状态为待审核
        blog.setStatus(0); // 0=待审核，需要管理员审核后才能显示

        // 设置默认值
        blog.setLiked(0);
        blog.setComments(0);
        blog.setIsTop(0);
        blog.setIsQuality(0);

        boolean isSuccess = save(blog);
        if (!isSuccess) {
            return Result.fail("发布博客失败！");
        }

        // 异步处理积分奖励
        cacheRebuildExecutor.submit(() -> {
            try {
                incentiveService.processUserAction(user.getId(), "POST_BLOG", "发布博客");
            } catch (Exception e) {
                log.error("处理发布博客积分奖励失败", e);
            }
        });

        return Result.ok(blog.getId());
    }

    /**
     * 【重要新增】补全缺失的 queryBlogById 方法
     * @param id 博客ID
     * @return 包含博客详情的Result
     */
    @Override
    public Result queryBlogById(Long id) {
        // 1. 使用我们之前在Mapper中定义的联表查询，获取博客和作者的基础信息
        BlogDTO blogDTO = baseMapper.queryBlogWithAuthor(id);
        if (blogDTO == null) {
            return Result.fail("博客不存在！");
        }

        // 【修复】检查博客状态，只允许查看审核通过的博客
        if (blogDTO.getStatus() != 1) {
            return Result.fail("博客不存在或未通过审核！");
        }
        // 2. 检查当前登录用户是否对该博客点过赞
        checkIsLiked(blogDTO);
        // 3. 设置收藏数
        setBlogFavoritesCount(blogDTO);
        return Result.ok(blogDTO);
    }

    @Override
    public Result queryHotBlogs(Integer current) {
        // 使用 LambdaQuery 方式并添加明确的 ORDER BY 子句，避免 SQL Server 在 OFFSET...FETCH 语法下缺失 ORDER BY 导致的语法错误
        Page<Blog> page = lambdaQuery()
                .eq(Blog::getStatus, 1) // 【修复】只显示审核通过的博客
                .orderByDesc(Blog::getLiked)   // 按点赞数倒序作为热门标准
                .orderByDesc(Blog::getCreateTime) // 次级排序：发布时间倒序
                .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
        List<Blog> records = page.getRecords();

        List<BlogDTO> dtoList = records.stream().map(blog -> {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtil.copyProperties(blog, blogDTO);

            User user = userService.getById(blog.getUserId());
            if (user != null) {
                blogDTO.setAuthorName(user.getNickname());
                blogDTO.setAuthorIcon(user.getIcon());
            }
            checkIsLiked(blogDTO);
            setBlogFavoritesCount(blogDTO); // 设置收藏数
            return blogDTO;
        }).collect(Collectors.toList());

        return Result.ok(dtoList, page.getTotal());
    }

    @Override
    public Result listBlogs(Integer current, Integer size, String sortBy) {
        if ("favorites".equals(sortBy)) {
            // 按收藏数排序需要特殊处理
            return listBlogsByFavorites(current, size);
        }

        Page<Blog> page = new Page<>(current, size);
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();

        // 【修复】只显示审核通过的博客
        wrapper.eq(Blog::getStatus, 1);

        switch (sortBy) {
            case "liked":
                wrapper.orderByDesc(Blog::getLiked);
                break;
            case "comments":
                wrapper.orderByDesc(Blog::getComments);
                break;
            default:
                wrapper.orderByDesc(Blog::getCreateTime);
        }
        page(page, wrapper);
        List<BlogDTO> list = page.getRecords().stream().map(blog -> {
            BlogDTO dto = new BlogDTO();
            BeanUtil.copyProperties(blog, dto);
            User user = userService.getById(blog.getUserId());
            if (user != null) {
                dto.setAuthorName(user.getNickname());
                dto.setAuthorIcon(user.getIcon());
            }
            checkIsLiked(dto);
            setBlogFavoritesCount(dto); // 设置收藏数
            return dto;
        }).collect(Collectors.toList());
        return Result.ok(list, page.getTotal());
    }

    /**
     * 按收藏数排序查询博客列表
     * @param current 当前页
     * @param size 页大小
     * @return 按收藏数排序的博客列表
     */
    private Result listBlogsByFavorites(Integer current, Integer size) {
        // 1. 先获取所有审核通过的博客
        List<Blog> allBlogs = lambdaQuery()
                .eq(Blog::getStatus, 1) // 【修复】只获取审核通过的博客
                .list();

        // 2. 转换为BlogDTO并设置收藏数
        List<BlogDTO> blogDTOs = allBlogs.stream().map(blog -> {
            BlogDTO dto = new BlogDTO();
            BeanUtil.copyProperties(blog, dto);
            User user = userService.getById(blog.getUserId());
            if (user != null) {
                dto.setAuthorName(user.getNickname());
                dto.setAuthorIcon(user.getIcon());
            }
            checkIsLiked(dto);
            setBlogFavoritesCount(dto);
            return dto;
        }).collect(Collectors.toList());

        // 3. 按收藏数排序
        blogDTOs.sort((a, b) -> {
            int favoritesA = a.getFavorites() != null ? a.getFavorites() : 0;
            int favoritesB = b.getFavorites() != null ? b.getFavorites() : 0;
            return Integer.compare(favoritesB, favoritesA); // 降序
        });

        // 4. 手动分页
        int start = (current - 1) * size;
        int end = Math.min(start + size, blogDTOs.size());
        List<BlogDTO> pagedList = start < blogDTOs.size() ?
            blogDTOs.subList(start, end) : Collections.emptyList();

        return Result.ok(pagedList, (long) blogDTOs.size());
    }

    @Override
    @Transactional
    public Result likeBlog(Long id) {
        Long userId = UserHolder.getUser().getId();
        String key = RedisConstants.BLOG_LIKED_KEY + id;

        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());

        if (score != null) {
            boolean success = update().setSql("liked = liked - 1").eq("id", id).update();
            if (success) {
                stringRedisTemplate.opsForZSet().remove(key, userId.toString());
            }
        } else {
            boolean success = update().setSql("liked = liked + 1").eq("id", id).update();
            if (success) {
                stringRedisTemplate.opsForZSet().add(key, userId.toString(), System.currentTimeMillis());
                createLikeNotification(id, userId);

                // 异步处理点赞积分奖励
                cacheRebuildExecutor.submit(() -> {
                    try {
                        incentiveService.processUserAction(userId, "LIKE_BLOG", "点赞博客");
                    } catch (Exception e) {
                        log.error("处理点赞博客积分奖励失败", e);
                    }
                });
            }
        }
        return Result.ok();
    }

    private void createLikeNotification(Long blogId, Long likerId) {
        cacheRebuildExecutor.submit(() -> {
            Blog blog = getById(blogId);
            if (blog != null && !blog.getUserId().equals(likerId)) {
                Notification notification = new Notification();
                notification.setId(redisIdWorker.nextId("notification"));
                notification.setRecipientId(blog.getUserId());
                notification.setSenderId(likerId);
                notification.setType(3);
                notification.setTargetId(blog.getId());
                notification.setTargetContent("您的博客《" + blog.getTitle() + "》被点赞了");
                notificationService.save(notification);
            }
        });
    }

    @Override
    public Result queryBlogLikes(Long id) {
        String key = RedisConstants.BLOG_LIKED_KEY + id;
        Set<String> top5 = stringRedisTemplate.opsForZSet().reverseRange(key, 0, 4);
        if (top5 == null || top5.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }

        List<Long> ids = top5.stream().map(Long::valueOf).collect(Collectors.toList());

        List<UserDTO> userDTOS = userService.listByIds(ids)
                .stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                .collect(Collectors.toList());

        return Result.ok(userDTOS);
    }

    private void checkIsLiked(BlogDTO blogDTO) {
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            blogDTO.setIsLiked(false);
            return;
        }
        String key = RedisConstants.BLOG_LIKED_KEY + blogDTO.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, user.getId().toString());
        blogDTO.setIsLiked(score != null);
    }

    /**
     * 从Redis获取博客的收藏数
     * @param blogId 博客ID
     * @return 收藏数
     */
    private Integer getBlogFavoritesCount(Long blogId) {
        String key = RedisConstants.BLOG_FAVORITES_KEY + blogId;
        Long count = stringRedisTemplate.opsForZSet().zCard(key);
        return count != null ? count.intValue() : 0;
    }

    /**
     * 为BlogDTO设置收藏数
     * @param blogDTO 博客DTO
     */
    private void setBlogFavoritesCount(BlogDTO blogDTO) {
        Integer favoritesCount = getBlogFavoritesCount(blogDTO.getId());
        blogDTO.setFavorites(favoritesCount);
    }

    /**
     * 对富文本内容进行安全过滤，保留允许的HTML标签
     * @param content 原始富文本内容
     * @return 过滤后的安全内容
     */
    private String sanitizeRichTextContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            return content;
        }

        // 允许的HTML标签和属性（适用于tiptap富文本编辑器）
        // 这里使用简单的白名单过滤，实际项目中建议使用专业的HTML清理库如OWASP Java HTML Sanitizer
        String[] allowedTags = {
            "p", "br", "strong", "b", "em", "i", "u", "s", "strike",
            "h1", "h2", "h3", "h4", "h5", "h6",
            "ul", "ol", "li",
            "blockquote", "pre", "code",
            "a", "img",
            "table", "thead", "tbody", "tr", "th", "td"
        };

        // 简单的HTML标签过滤（生产环境建议使用专业库）
        String sanitized = content;

        // 移除script、style等危险标签
        sanitized = sanitized.replaceAll("(?i)<script[^>]*>.*?</script>", "");
        sanitized = sanitized.replaceAll("(?i)<style[^>]*>.*?</style>", "");
        sanitized = sanitized.replaceAll("(?i)<iframe[^>]*>.*?</iframe>", "");
        sanitized = sanitized.replaceAll("(?i)<object[^>]*>.*?</object>", "");
        sanitized = sanitized.replaceAll("(?i)<embed[^>]*>.*?</embed>", "");

        // 移除危险的事件属性
        sanitized = sanitized.replaceAll("(?i)\\s*on\\w+\\s*=\\s*[\"'][^\"']*[\"']", "");
        sanitized = sanitized.replaceAll("(?i)\\s*on\\w+\\s*=\\s*[^\\s>]+", "");

        // 移除javascript:协议
        sanitized = sanitized.replaceAll("(?i)javascript:", "");

        return sanitized;
    }
}