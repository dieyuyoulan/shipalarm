package com.example.springbootquant.entil;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("message")
@Data
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String time;
    private String topic;
    private String x;
    private String y;
    private String yearMonthDay;
}
