package com.example.springbootquant.entil;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("ship")
@Data
public class Ship {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String shipType;
    private String shipName;
    private String shipCompany;
    private String captain;
    private String isWork;
    private String startOn;
    private String endOn;
    private String startDate;//启航时间
    private String foresDate;//预期到达时间
    private String adventDate;
    private String isAdvent; //是否确认到达
    private String isAlarm;//是否报警
}
