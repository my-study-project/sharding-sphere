package com.js.service.impl;

import com.js.mapper.UserMapper;
import com.js.service.UserService;
import com.js.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper UserMapper;

    @Override
    public UserMapper getUserMapper() {
        return UserMapper;
    }

    @Override
    public User findById(Long id) {
        return UserMapper.findById(id);
    }
    @Override
    public List<User> find(User User) {
        return UserMapper.find(User);
    }
    @Override
    public int insert(User User) {
        return UserMapper.insert(User);
    }
    @Override
    public int batchInsert(List<User> list) {
        return UserMapper.batchInsert(list);
    }
    @Override
    public int updateById(User User) {
        return UserMapper.updateById(User);
    }
    @Override
    public int updateBatch(List<User> list) {
        return UserMapper.updateBatch(list);
    }
    @Override
    public int deleteById(Long id) {
        return UserMapper.deleteById(id);
    }
    @Override
    public int deleteByEntity(User User) {
        return UserMapper.deleteByEntity(User);
    }
    @Override
    public int deleteByIds(List<Long> list) {
        return UserMapper.deleteByIds(list);
    }
    @Override
    public int countByEntity(User User) {
        return UserMapper.countByEntity(User);
    }

}