package com.js;

import com.js.mapper.UserMapper;
import com.js.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingEncryptApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User();
        user.setUserName("test1");
        user.setGender("2");
        user.setPassword("test1-test");
        userMapper.insert(user);
    }

}
