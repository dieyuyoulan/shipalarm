package com.example.springbootquant.controllor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootquant.VO.LineVO;
import com.example.springbootquant.common.Result;
import com.example.springbootquant.entil.Message;
import com.example.springbootquant.service.ShipService;
import com.example.springbootquant.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/task")
public class TaskControllor {

    @Resource
    TaskService taskService;

    @Resource
    ShipService shipService;

    /* 保存事件到数据库 */
    @GetMapping("/save")
    public Result<?> saveTask(@CookieValue(value = "username",defaultValue = "") String username,
                              @RequestParam String x,
                              @RequestParam String y,
                              @RequestParam String topic,
                              @RequestParam String time){
        System.out.println(username);
        Message message = new Message();
        message.setTime(time);
        message.setTopic(topic);
        message.setX(x);
        message.setY(y);
        System.out.println(message);
        taskService.save(message);
        return Result.success();
    }

    /* 展示事件信息到页面 */
    @GetMapping("/display")
    public Result<?> findPage(@RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        if (StringUtils.isNotBlank(search)) {
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("topic", search);
            Page<Message> messagePage = taskService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(messagePage);
        }
        Page<Message> messagePage = taskService.page(new Page<>(pageNum, pageSize));
        return Result.success(messagePage);

    }

    @DeleteMapping("/deleteTask" + "/{id}")
    public Result<?> update(@PathVariable Integer id) {
        taskService.removeById(id);
        return Result.success();
    }

    @GetMapping("/lineVO")
    public LineVO lineVO() throws ParseException {
        return taskService.lineAlarm();
    }
}
