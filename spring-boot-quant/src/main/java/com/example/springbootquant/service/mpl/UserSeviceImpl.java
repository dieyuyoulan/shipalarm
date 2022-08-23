package com.example.springbootquant.service.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootquant.entil.User;
import com.example.springbootquant.mapper.UserMapper;
import com.example.springbootquant.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserSeviceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public User checklog(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUsername()).eq("password", user.getPassword());
        User res = userMapper.selectOne(userQueryWrapper);
        return res;
    }
}
