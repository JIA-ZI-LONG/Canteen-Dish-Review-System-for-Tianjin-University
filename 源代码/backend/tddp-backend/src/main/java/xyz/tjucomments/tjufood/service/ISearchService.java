package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.dto.Result;

public interface ISearchService {
    Result search(String keyword, String type, Integer current, Integer size, Long userId);
}
