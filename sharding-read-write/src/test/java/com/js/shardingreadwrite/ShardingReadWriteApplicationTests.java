package com.js.shardingreadwrite;

import com.js.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingReadWriteApplicationTests {

    @Autowired
    private TestService testService;
    @Test
    void contextLoads() {
        System.out.println(testService.test());
    }

}
