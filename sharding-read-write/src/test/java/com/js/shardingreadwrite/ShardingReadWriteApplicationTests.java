package com.js.shardingreadwrite;

import com.js.pojo.User;
import com.js.service.TestService;
import com.js.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShardingReadWriteApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
//        User user = new User();
//        user.setGender("0");
//        user.setUserName("testWriteRead");
//        user.setPassword("testWriteRead");
//        userService.doSave(user);
        final List<User> all = userService.findAll(new User());
        System.out.println(all);
    }

}
