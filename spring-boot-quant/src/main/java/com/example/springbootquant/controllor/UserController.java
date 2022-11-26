package com.example.springbootquant.controllor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootquant.common.Result;
import com.example.springbootquant.entil.Ship;
import com.example.springbootquant.entil.User;
import com.example.springbootquant.service.ShipService;
import com.example.springbootquant.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @Resource
    ShipService shipService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        Cookie cookie = new Cookie("username", user.getUsername());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.addCookie(cookie);
        System.out.println(cookie.getValue());
       // System.out.println(user);
        User res = userService.checklog(user);
        if(res == null){
            return Result.error("-1", "用户名或密码错误！");
        }
        return Result.success();
    }

    @PostMapping("/res")
    public Result<?> res(@RequestBody User user){
        userService.save(user);
        return Result.success();
    }

    @GetMapping("/display")
    public Result<?> findPage(@RequestParam(defaultValue = "") String company,
                              @RequestParam(defaultValue = "") String section,
                              @RequestParam(defaultValue = "") String position,
                              @RequestParam(defaultValue = "") String username,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        if (StringUtils.isNotBlank(company)) {
            if(StringUtils.isNotBlank(section)){
                if(StringUtils.isNotBlank(position)){
                    if(StringUtils.isNotBlank(username)){
                        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                        userQueryWrapper.eq("commpany", company).eq("section", section)
                                .eq("position", position)
                                .eq("username", username);
                        Page<User> userPage = userService.page(new Page<>(pageNum, pageSize), userQueryWrapper);
                        return Result.success(userPage);
                    }else {
                        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                        userQueryWrapper.eq("company", company).eq("section", section).eq("position", position);
                        Page<User> userPage = userService.page(new Page<>(pageNum, pageSize), userQueryWrapper);
                        return Result.success(userPage);
                    }
                }
                else {
                    QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                    userQueryWrapper.eq("company", company).eq("section", section);
                    Page<User> userPage = userService.page(new Page<>(pageNum, pageSize), userQueryWrapper);
                    return Result.success(userPage);
                }
            }else{
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("company", company);
                Page<User> userPage = userService.page(new Page<>(pageNum, pageSize), userQueryWrapper);
                return Result.success(userPage);
            }
        }else {
            if(StringUtils.isNotBlank(section) || StringUtils.isNotBlank(position)){
                return Result.error("-1", "公司名称不能为空");
            }else{
                if(StringUtils.isNotBlank(username)){
                    QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                    userQueryWrapper.eq("username", username);
                    Page<User> userPage = userService.page(new Page<>(pageNum, pageSize), userQueryWrapper);
                    return Result.success(userPage);
                }else{
                    Page<User> accidentPage = userService.page(new Page<>(pageNum, pageSize));
                    return Result.success(accidentPage);
                }
            }
        }
    }

    @GetMapping("/advent")
    public Result<?> advent(@CookieValue(value = "username",defaultValue = "") String username,
                            @RequestParam String adventDate){
        System.out.println(username);
        QueryWrapper<Ship> shipQueryWrapper = new QueryWrapper<>();
        shipQueryWrapper.eq("captain", username);
        Ship ship = shipService.getOne(shipQueryWrapper);
        ship.setAdventDate(adventDate);//设置到达时间
        ship.setIsAdvent("是");
        ship.setIsWork("否");
        shipService.updateById(ship);
        return Result.success();
    }

    @GetMapping("/user")
    public Result<?> user(@CookieValue(value = "username",defaultValue = "") String username){
        System.out.println(username);
        return Result.success(username);
    }
}
