package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.SearchHistory;
import xyz.tjucomments.tjufood.mapper.SearchHistoryMapper;
import xyz.tjucomments.tjufood.service.ISearchHistoryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements ISearchHistoryService {

    @Override
    public void saveHistory(Long userId, String keyword) {
        if (userId == null || keyword == null || keyword.isBlank()) {
            return;
        }
        // 如果已存在则仅更新时间
        SearchHistory existing = lambdaQuery().eq(SearchHistory::getUserId, userId)
                .eq(SearchHistory::getKeyword, keyword)
                .one();
        if (existing != null) {
            existing.setCreateTime(LocalDateTime.now());
            updateById(existing);
        } else {
            SearchHistory h = new SearchHistory();
            h.setId(System.currentTimeMillis());
            h.setUserId(userId);
            h.setKeyword(keyword);
            h.setCreateTime(LocalDateTime.now());
            save(h);
        }
    }

    @Override
    public List<SearchHistory> listLatest(Long userId, int limit) {
        return lambdaQuery().eq(SearchHistory::getUserId, userId)
                .orderByDesc(SearchHistory::getCreateTime)
                .last("limit " + limit)
                .list();
    }

    @Override
    public void clearHistory(Long userId) {
        lambdaUpdate().eq(SearchHistory::getUserId, userId).remove();
    }
}
