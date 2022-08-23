package com.example.springbootquant.entil;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("reg")
@Data
public class User {
    @TableId()
    private Integer id;
    private String username;
    private String password;
    private String position;
    private String section;
    private String company;
}
