package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.DishDTO;
import xyz.tjucomments.tjufood.entity.*;
import xyz.tjucomments.tjufood.mapper.*;
import xyz.tjucomments.tjufood.service.IRankingService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankingServiceImpl implements IRankingService {

    @Resource
    private CanteenMapper canteenMapper;
    
    @Resource
    private DishMapper dishMapper;
    
    @Resource
    private BlogMapper blogMapper;
    
    @Resource
    private UserMapper userMapper;

    @Override
    public Result getCanteenRanking(String sortBy, Integer limit) {
        try {
            LambdaQueryWrapper<Canteen> queryWrapper = new LambdaQueryWrapper<>();

            // 根据排序方式设置排序条件
            switch (sortBy) {
                case "likes":
                    // liked字段不存在于数据库表中，使用评分代替
                    queryWrapper.orderByDesc(Canteen::getScore);
                    break;
                case "comments":
                    queryWrapper.orderByDesc(Canteen::getComments);
                    break;
                case "score":
                default:
                    queryWrapper.orderByDesc(Canteen::getScore);
                    break;
            }

            queryWrapper.last("OFFSET 0 ROWS FETCH NEXT " + limit + " ROWS ONLY");
            List<Canteen> canteens = canteenMapper.selectList(queryWrapper);

            // 转换为排行榜格式
            List<Map<String, Object>> rankingList = new ArrayList<>();
            for (int i = 0; i < canteens.size(); i++) {
                Canteen canteen = canteens.get(i);
                Map<String, Object> item = new HashMap<>();
                item.put("rank", i + 1);
                item.put("id", canteen.getId());
                item.put("name", canteen.getName());
                item.put("address", canteen.getAddress());
                item.put("averageScore", canteen.getScore());
                // liked字段从Redis或其他地方获取，这里暂时设为0
                item.put("liked", 0);
                item.put("comments", canteen.getComments());
                rankingList.add(item);
            }

            return Result.ok(rankingList);
        } catch (Exception e) {
            return Result.fail("获取食堂排行榜失败：" + e.getMessage());
        }
    }

    @Override
    public Result getDishRanking(String sortBy, Integer limit, Long canteenId) {
        try {
            // 使用联表查询获取菜品排行榜
            List<DishDTO> dishes = dishMapper.getDishRankingWithJoin(canteenId, sortBy, limit);

            // 转换为排行榜格式
            List<Map<String, Object>> rankingList = new ArrayList<>();
            for (int i = 0; i < dishes.size(); i++) {
                DishDTO dish = dishes.get(i);
                Map<String, Object> item = new HashMap<>();
                item.put("rank", i + 1);
                item.put("id", dish.getId());
                item.put("name", dish.getName());
                item.put("description", dish.getDescription());
                item.put("price", dish.getPrice());
                item.put("averageScore", 0.0); // Dish实体中没有averageScore字段，使用默认值
                item.put("liked", dish.getLiked());
                item.put("sales", 0); // Dish实体中没有sales字段，使用默认值
                item.put("stallName", dish.getStallName()); // 通过联表查询获得的窗口名称
                item.put("canteenName", dish.getCanteenName()); // 通过联表查询获得的食堂名称
                rankingList.add(item);
            }

            return Result.ok(rankingList);
        } catch (Exception e) {
            return Result.fail("获取菜品排行榜失败：" + e.getMessage());
        }
    }

    @Override
    public Result getBlogRanking(String sortBy, Integer limit, String timeRange) {
        try {
            LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
            
            // 根据时间范围设置过滤条件
            LocalDateTime startTime = null;
            switch (timeRange) {
                case "week":
                    startTime = LocalDateTime.now().minusWeeks(1);
                    break;
                case "month":
                    startTime = LocalDateTime.now().minusMonths(1);
                    break;
                case "all":
                default:
                    // 不设置时间限制
                    break;
            }
            
            if (startTime != null) {
                queryWrapper.ge(Blog::getCreateTime, startTime);
            }
            
            // 根据排序方式设置排序条件
            switch (sortBy) {
                case "comments":
                    queryWrapper.orderByDesc(Blog::getComments);
                    break;
                case "views":
                    // 假设有浏览量字段，如果没有可以用点赞数代替
                    queryWrapper.orderByDesc(Blog::getLiked);
                    break;
                case "likes":
                default:
                    queryWrapper.orderByDesc(Blog::getLiked);
                    break;
            }
            
            queryWrapper.last("OFFSET 0 ROWS FETCH NEXT " + limit + " ROWS ONLY");
            List<Blog> blogs = blogMapper.selectList(queryWrapper);
            
            // 转换为排行榜格式，需要关联用户信息
            List<Map<String, Object>> rankingList = new ArrayList<>();
            for (int i = 0; i < blogs.size(); i++) {
                Blog blog = blogs.get(i);
                User user = userMapper.selectById(blog.getUserId());
                
                Map<String, Object> item = new HashMap<>();
                item.put("rank", i + 1);
                item.put("id", blog.getId());
                item.put("title", blog.getTitle());
                item.put("nickname", user != null ? user.getNickname() : "未知用户");
                item.put("liked", blog.getLiked());
                item.put("comments", blog.getComments());
                item.put("createTime", blog.getCreateTime());
                rankingList.add(item);
            }
            
            return Result.ok(rankingList);
        } catch (Exception e) {
            return Result.fail("获取博客排行榜失败：" + e.getMessage());
        }
    }

    @Override
    public Result getUserRanking(String sortBy, Integer limit) {
        try {
            // 获取所有用户，然后根据统计数据排序
            List<User> allUsers = userMapper.selectList(new LambdaQueryWrapper<User>());

            // 计算每个用户的统计数据并排序
            List<Map<String, Object>> userStats = new ArrayList<>();
            for (User user : allUsers) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", user.getId());
                item.put("nickname", user.getNickname());
                item.put("intro", user.getBio());
                item.put("credits", user.getCredits());

                // 统计用户的博客数量
                LambdaQueryWrapper<Blog> blogQuery = new LambdaQueryWrapper<>();
                blogQuery.eq(Blog::getUserId, user.getId());
                Long blogCount = blogMapper.selectCount(blogQuery);
                item.put("blogCount", blogCount);

                // 统计用户获得的总点赞数（统计该用户所有博客的点赞数）
                List<Blog> userBlogs = blogMapper.selectList(blogQuery);
                int totalLikes = userBlogs.stream().mapToInt(blog -> blog.getLiked() != null ? blog.getLiked() : 0).sum();
                item.put("totalLikes", totalLikes);

                userStats.add(item);
            }

            // 根据排序方式排序
            switch (sortBy) {
                case "blogs":
                    userStats.sort((a, b) -> Long.compare((Long) b.get("blogCount"), (Long) a.get("blogCount")));
                    break;
                case "likes":
                    userStats.sort((a, b) -> Integer.compare((Integer) b.get("totalLikes"), (Integer) a.get("totalLikes")));
                    break;
                case "credits":
                default:
                    userStats.sort((a, b) -> Integer.compare((Integer) b.get("credits"), (Integer) a.get("credits")));
                    break;
            }

            // 取前limit个并添加排名
            List<Map<String, Object>> rankingList = new ArrayList<>();
            for (int i = 0; i < Math.min(limit, userStats.size()); i++) {
                Map<String, Object> item = userStats.get(i);
                item.put("rank", i + 1);
                rankingList.add(item);
            }

            return Result.ok(rankingList);
        } catch (Exception e) {
            return Result.fail("获取用户排行榜失败：" + e.getMessage());
        }
    }

    @Override
    public Result getRankingSummary() {
        try {
            Map<String, Object> summary = new HashMap<>();
            
            // 获取各类排行榜的前3名
            Result canteenResult = getCanteenRanking("score", 3);
            Result dishResult = getDishRanking("score", 3, null);
            Result blogResult = getBlogRanking("likes", 3, "week");
            Result userResult = getUserRanking("credits", 3);
            
            summary.put("topCanteens", canteenResult.getData());
            summary.put("topDishes", dishResult.getData());
            summary.put("topBlogs", blogResult.getData());
            summary.put("topUsers", userResult.getData());
            
            return Result.ok(summary);
        } catch (Exception e) {
            return Result.fail("获取综合排行榜失败：" + e.getMessage());
        }
    }
}
