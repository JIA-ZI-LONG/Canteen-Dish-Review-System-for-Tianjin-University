// src/main/java/xyz/tjucomments/tjufood/service/ICommentService.java
package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Comment;

public interface ICommentService extends IService<Comment> {
    Result addComment(Comment comment);
    Result queryCommentsByTarget(Long targetId, Integer type);
    /**
     * 分页查询当前登录用户的评论列表
     */
    Result queryCommentsByUser(Integer current, Integer size);
}