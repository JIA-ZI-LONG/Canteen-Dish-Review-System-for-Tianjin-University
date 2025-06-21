package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Canteen;

public interface ICanteenService extends IService<Canteen> {

    // 保留您原有的方法
    Result listCanteens(Long campusId);

    // 保留您原有的方法
    Result queryCanteenById(Long id);


    Result pageQueryForAdmin(Integer current, Integer size);

}