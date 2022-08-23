package com.example.springbootquant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootquant.VO.LineVO;
import com.example.springbootquant.entil.Message;

import java.text.ParseException;

public interface TaskService extends IService<Message> {
    public LineVO lineAlarm() throws ParseException;
}
