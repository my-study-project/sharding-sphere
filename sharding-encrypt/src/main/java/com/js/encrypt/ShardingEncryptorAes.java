package com.js.encrypt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.encrypt.strategy.spi.Encryptor;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Data
@Service
public class ShardingEncryptorAes implements Encryptor {

    @Override
    public String getType() {
        return "my-aes";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public void init() {

    }

    @Override
    public String encrypt(Object o) {
        return null;
    }

    @Override
    public Object decrypt(String s) {
        return null;
    }
}
