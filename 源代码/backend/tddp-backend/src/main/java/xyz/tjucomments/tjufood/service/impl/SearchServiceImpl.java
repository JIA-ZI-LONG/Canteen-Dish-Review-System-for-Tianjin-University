package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Blog;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.mapper.BlogMapper;
import xyz.tjucomments.tjufood.mapper.CanteenMapper;
import xyz.tjucomments.tjufood.mapper.DishMapper;
import xyz.tjucomments.tjufood.mapper.StallMapper;
import xyz.tjucomments.tjufood.service.ISearchHistoryService;
import xyz.tjucomments.tjufood.service.ISearchService;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.Map;
import java.util.function.Function;

@Service
public class SearchServiceImpl implements ISearchService {

    @Resource
    private CanteenMapper canteenMapper;
    @Resource
    private DishMapper dishMapper;
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private StallMapper stallMapper;
    @Resource
    private ISearchHistoryService searchHistoryService;

    @Override
    public Result search(String keyword, String type, Integer current, Integer size, Long userId) {
        // 保存历史（异步可优化）
        if (userId != null) {
            searchHistoryService.saveHistory(userId, keyword);
        }

        // 根据类型路由
        Map<String, Object> data = new HashMap<>();
        switch (type == null ? "all" : type) {
            case "canteen":
                data.putAll(handle(keyword, current, size, canteenMapper::selectPage, Canteen::getName));
                break;
            case "dish":
                data.putAll(handle(keyword, current, size, dishMapper::selectPage, Dish::getName));
                break;
            case "blog":
                data.putAll(handleBlog(keyword, current, size));
                break;
            case "stall":
                data.putAll(handle(keyword, current, size, stallMapper::selectPage, Stall::getName));
                break;
            default:
                // all 类型：只查食堂&菜品&窗口&博客前三条即可
                data.put("canteens", handle(keyword, 1, 3, canteenMapper::selectPage, Canteen::getName).get("records"));
                data.put("dishes", handle(keyword, 1, 3, dishMapper::selectPage, Dish::getName).get("records"));
                data.put("stalls", handle(keyword, 1, 3, stallMapper::selectPage, Stall::getName).get("records"));
                data.put("blogs", handleBlog(keyword, 1, 3).get("records"));
                return Result.ok(data);
        }
        return Result.ok(data);
    }

    /**
     * 【新增】专门处理博客搜索，过滤审核状态
     */
    private Map<String, Object> handleBlog(String keyword, Integer current, Integer size) {
        Page<Blog> page = new Page<>(current, size);
        QueryWrapper<Blog> qw = new QueryWrapper<>();
        qw.like("title", keyword)
                .eq("status", 1) // 【修复】只搜索审核通过的博客
                .orderByDesc("create_time");
        blogMapper.selectPage(page, qw);

        // 高亮处理
        for (Blog blog : page.getRecords()) {
            String title = blog.getTitle();
            if (title != null) {
                String highlighted = title.replaceAll("(?i)" + keyword, "<em class=\"highlight\">$0</em>");
                blog.setTitle(highlighted);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("records", page.getRecords());
        result.put("total", page.getTotal());
        return result;
    }

    private <T> Map<String, Object> handle(String keyword, Integer current, Integer size,
                                           BiConsumer<Page<T>, QueryWrapper<T>> executor,
                                           Function<T, String> fieldGetter) {
        Page<T> page = new Page<>(current, size);
        QueryWrapper<T> qw = new QueryWrapper<>();
        qw.like("name", keyword).or().like("title", keyword)
                .orderByDesc("create_time");
        executor.accept(page, qw);
        // 高亮处理
        for (T r : page.getRecords()) {
            String src = fieldGetter.apply(r);
            if (src != null) {
                String hl = src.replaceAll("(?i)" + keyword, "<em class=\\\"highlight\\\">$0</em>");
                if (r instanceof Canteen c) c.setName(hl);
                else if (r instanceof Dish d) d.setName(hl);
                else if (r instanceof Stall s) s.setName(hl);
                else if (r instanceof Blog b) b.setTitle(hl);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("records", page.getRecords());
        map.put("total", page.getTotal());
        return map;
    }

    // functional interface to adapt mapper.selectPage
    
}
