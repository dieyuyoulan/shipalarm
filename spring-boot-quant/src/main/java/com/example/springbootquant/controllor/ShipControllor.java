package com.example.springbootquant.controllor;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootquant.VO.Data1;
import com.example.springbootquant.common.Result;
import com.example.springbootquant.entil.Ship;
import com.example.springbootquant.entil.User;
import com.example.springbootquant.service.ShipService;
import com.example.springbootquant.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ship")
public class ShipControllor {
    @Resource
    ShipService shipService;

    @Resource
    UserService userService;
    @GetMapping("/display")
    public Result<?> findPage(@RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        if (StringUtils.isNotBlank(search)) {
            QueryWrapper<Ship> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ship_name", search);
            Page<Ship> messagePage = shipService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(messagePage);
        }
        Page<Ship> messagePage = shipService.page(new Page<>(pageNum, pageSize));
        return Result.success(messagePage);

    }

    @DeleteMapping("/delete" + "/{id}")
    public Result<?> update(@PathVariable Integer id) {
        shipService.removeById(id);
        return Result.success();
    }

    //新增船舶信息
    @PostMapping("/save")
    public Result<?> save(@RequestBody Ship ship){
        ship.setIsWork("否");
        ship.setIsAlarm("否");//设置初始值
        shipService.save(ship);
        return Result.success();
    }

    @PostMapping("/date")
    public Result<?> date(@RequestBody Ship ship){
        try{
            ship.setIsWork("是");//设置是否排期
            ship.setIsAdvent("否");
            shipService.updateById(ship);
        }catch (Exception e){
            return Result.error("-1", "排期失败");
        }
        return Result.success();

    }

    @PostMapping("/getCaptain")
    public Result<?> getCaptain(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("position", "船长");
        List<User> userList = userService.list(queryWrapper);
        return Result.success(userList);
    }

    @GetMapping("/bear")
    public Data1 bear() {
        return shipService.Bear();
    }
}
