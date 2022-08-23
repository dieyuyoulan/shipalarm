package com.example.springbootquant.service.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootquant.VO.LineVO;
import com.example.springbootquant.entil.Message;
import com.example.springbootquant.entil.Ship;
import com.example.springbootquant.mapper.ShipMapper;
import com.example.springbootquant.mapper.TaskMapper;
import com.example.springbootquant.service.ShipService;
import com.example.springbootquant.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Message> implements TaskService {

    int count = 0;
    @Resource
    TaskMapper taskMapper;

    @Resource
    ShipMapper shipMapper;

    @Resource
    ShipService shipService;
    //预警折线

    @Override
    public LineVO lineAlarm() throws ParseException {

        List<String> dates = new ArrayList<>();
        List<Integer> alarmCount = new ArrayList<>();
        List<Integer> startCount = new ArrayList<>();
        LineVO lineVO = new LineVO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/d");
        Date date = new Date(System.currentTimeMillis());
        String schedDate = dateFormat.format(date);
        String schedDate1 = dateFormat.format(new Date(date.getTime() - 24 * 60 * 60 * 1000));
        String schedDate2 = dateFormat.format(new Date(date.getTime() - 2 * 24 * 60 * 60 * 1000));
        String schedDate3 = dateFormat.format(new Date(date.getTime() - 3 * 24 * 60 * 60 * 1000));
        String schedDate4 = dateFormat.format(new Date(date.getTime() - 4 * 24 * 60 * 60 * 1000));
        String schedDate5 = dateFormat.format(new Date(date.getTime() - 5 * 24 * 60 * 60 * 1000));
        String schedDate6 = dateFormat.format(new Date(date.getTime() - 6 * 24 * 60 * 60 * 1000));
        dates.add(schedDate6);
        dates.add(schedDate5);
        dates.add(schedDate4);
        dates.add(schedDate3);
        dates.add(schedDate2);
        dates.add(schedDate1);
        dates.add(schedDate);
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        List<Message> messages = taskMapper.selectList(messageQueryWrapper);
        for(Message message : messages){
            message.setYearMonthDay(message.getTime().split(" ")[0]);
            System.out.println(message.getYearMonthDay());
            taskMapper.updateById(message);
        }
        for(String str : dates){
            int count = count(new QueryWrapper<Message>().eq("year_month_day", str));
            System.out.println(str);
            alarmCount.add(count);
            System.out.println(count);
        }
        for(String str : dates){
            String year = str.split("/")[0];
            String month = str.split("/")[1];
            String day = str.split("/")[2];
            String str1 = year + "-" + month + "-" + day;
            int count = shipService.count(new QueryWrapper<Ship>().eq("start_date", str1));
            startCount.add(count);
        }
        lineVO.setDates(dates);
        lineVO.setAlarmCount(alarmCount);
        lineVO.setStartCount(startCount);
        return lineVO;
    }
}
