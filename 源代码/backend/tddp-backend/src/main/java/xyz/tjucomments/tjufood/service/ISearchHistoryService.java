package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.entity.SearchHistory;

import java.util.List;

public interface ISearchHistoryService extends IService<SearchHistory> {
    void saveHistory(Long userId, String keyword);
    List<SearchHistory> listLatest(Long userId, int limit);
    void clearHistory(Long userId);
}
