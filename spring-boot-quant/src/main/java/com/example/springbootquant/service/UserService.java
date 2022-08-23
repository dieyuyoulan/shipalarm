package com.example.springbootquant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootquant.entil.User;
import org.springframework.stereotype.Service;


public interface UserService extends IService<User> {
    public User checklog(User user);
}
