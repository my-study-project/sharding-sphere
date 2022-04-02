package com.js.service;

import com.js.mapper.UserMapper;
import com.js.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void doSave(User user){
        userMapper.insert(user);
    }

    public List<User> findAll(User user){
        return userMapper.find(user);
    }
}
