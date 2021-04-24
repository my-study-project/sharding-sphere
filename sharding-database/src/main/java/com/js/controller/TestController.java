package com.js.controller;

import com.js.mapper.UserMapper;
import com.js.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("test")
    public void contextLoads() {
        User condition = new User();
        System.out.println(userMapper.selectList(condition));
    }
}
