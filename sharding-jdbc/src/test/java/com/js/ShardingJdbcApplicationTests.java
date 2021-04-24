package com.js;

import com.js.mapper.UserMapper;
import com.js.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingJdbcApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User condition = new User();
        System.out.println(userMapper.selectList(condition));
    }

}
