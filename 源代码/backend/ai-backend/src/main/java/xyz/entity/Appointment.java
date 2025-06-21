package xyz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("food_reservations")
public class Appointment {

       @TableId(type = IdType.AUTO)
       private Long id;
       private String username;
       private String studentId;
       private String cafeteria;  // 食堂名称
       private String date;
       private String time;
       private String chefName;   // 厨师/档口名称
       private String dishType;   // 菜品类型
}