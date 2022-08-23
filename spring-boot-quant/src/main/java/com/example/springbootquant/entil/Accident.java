package com.example.springbootquant.entil;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("accident")
public class Accident {

    @TableId(type = IdType.AUTO)
    /**
     * 事件ID
     */
    private Integer id;
    /**
     * 事件
     */
    private String accidence;
    /**
     * 主题
     */
    private String topic;

}
