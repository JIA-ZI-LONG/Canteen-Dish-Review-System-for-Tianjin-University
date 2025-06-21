package xyz.tjucomments.aimodel;

import xyz.entity.Appointment;
import xyz.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppointmentTools {
    @Autowired
    private AppointmentService appointmentService;


    @Tool(name="预约用餐", value = "根据参数，先执行工具方法queryCafeteria查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约。如果用户没有提供具体的厨师姓名，请从向量存储中找到一位厨师。")
    public String bookFoodReservation(Appointment foodReservation){
            //查找数据库中是否包含对应的预约记录
            Appointment reservationDB = appointmentService.getOne(foodReservation);

            if(reservationDB == null){
                foodReservation.setId(null);//防止大模型幻觉设置了id
                if(appointmentService.save(foodReservation)){
                    return "用餐预约成功，并返回预约详情";
                }else{
                    return "用餐预约失败";
                }
            }

            return "您在相同的食堂和时间已有预约";
    }

    @Tool(name="取消用餐预约", value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
    public String cancelFoodReservation(Appointment foodReservation){
        Appointment reservationDB = appointmentService.getOne(foodReservation);
        if(reservationDB != null){
            //删除预约记录
            if(appointmentService.removeById(reservationDB.getId())){
                return "取消用餐预约成功";
            }else{
                return "取消用餐预约失败";
            }
        }
        //取消失败
        return "您没有用餐预约记录，请核对预约食堂和时间";
    }

    /**
     * 查询指定食堂、时间是否有可用餐位
     *
     * 警告：此方法的业务逻辑尚未完成实现！
     * 当前总是返回 true，这会导致 AI 代理错误地认为所有预约都可行。
     *
     * 需要实现的功能：
     * 1. 厨师排班管理系统
     * 2. 餐位容量管理
     * 3. 预约冲突检测
     *
     * @param name 食堂名称
     * @param date 预约日期
     * @param time 用餐时间（早餐、午餐、晚餐）
     * @param chefName 指定厨师名称（可选）
     * @return 是否有可用餐位
     */
    @Tool(name = "查询是否有餐位", value="根据食堂名称，日期，时间和厨师查询是否有餐位，并返回给用户")
    public boolean queryCafeteria(
            @P(value = "食堂名称") String name,
            @P(value = "日期") String date,
            @P(value = "时间，可选值：早餐、午餐、晚餐") String time,
            @P(value = "厨师名称", required = false) String chefName
    ) {
        System.out.println("查询是否有餐位");
        System.out.println("食堂名称：" + name);
        System.out.println("日期：" + date);
        System.out.println("时间：" + time);
        System.out.println("厨师名称：" + chefName);

        /*
         * TODO: 实现完整的餐位查询逻辑
         *
         * 实现步骤：
         * 1. 创建厨师排班表（Chef Schedule）数据模型
         *    - 厨师ID、姓名
         *    - 工作日期、时间段
         *    - 所属食堂
         *    - 最大服务人数
         *
         * 2. 查询逻辑：
         *    a) 如果未指定厨师：
         *       - 查询指定食堂、日期、时间段的所有在班厨师
         *       - 计算总可用餐位数
         *       - 查询已有预约数量
         *       - 返回是否还有空余餐位
         *
         *    b) 如果指定了厨师：
         *       - 验证厨师在指定时间是否有排班
         *       - 查询该厨师的最大服务容量
         *       - 查询该厨师已有预约数量
         *       - 返回是否还有空余餐位
         *
         * 3. 需要的数据库表：
         *    - chef_schedules (厨师排班表)
         *    - chef_info (厨师信息表)
         *    - cafeteria_capacity (食堂容量配置表)
         *
         * 4. 业务规则：
         *    - 预约需要提前至少2小时
         *    - 每个厨师有最大服务人数限制
         *    - 需要考虑节假日特殊排班
         */

        // 临时返回 true - 这是不正确的实现！
        // 在生产环境中，这会导致超额预约和用户体验问题
        System.out.println("警告：餐位查询逻辑未实现，总是返回 true");
        return true;
    }
}
