package com.js;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.js.mapper")
public class ShardingEncryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingEncryptApplication.class, args);
    }

}
