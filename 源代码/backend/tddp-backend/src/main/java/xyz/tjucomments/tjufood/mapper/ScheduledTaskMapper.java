package xyz.tjucomments.tjufood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.tjucomments.tjufood.entity.ScheduledTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务Mapper接口
 */
@Mapper
public interface ScheduledTaskMapper extends BaseMapper<ScheduledTask> {
}
