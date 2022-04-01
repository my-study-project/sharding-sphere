package com.js;

import com.alibaba.fastjson.JSON;
import com.js.mapper.UserMapper;
import com.js.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShardingEncryptApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User();
        user.setId(2);
        user.setUserName("200");
        user.setPassword("400");
//        user.setGender("2");
//        user.setPassword("test1-test");
//        final List<User> users = userMapper.find(user);
//        System.out.println(JSON.toJSONString(users));
        final Integer integer = userMapper.updateById(user);
        final User byId = userMapper.findById(2);
        System.out.println(JSON.toJSONString(byId));
    }

}
