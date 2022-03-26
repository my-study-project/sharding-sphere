//package com.js.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.encrypt.api.EncryptRuleConfiguration;
//import org.apache.shardingsphere.encrypt.api.EncryptorRuleConfiguration;
//import org.apache.shardingsphere.shardingjdbc.api.EncryptDataSourceFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.activation.DataSource;
//import java.sql.SQLException;
//import java.util.Properties;
//
//@Configuration
//@Slf4j
//public class DateSourceConfig {
//    @Bean("tradePlatformDataSource")
//    public DataSource dataSource(@Qualifier("druidDataSource") DataSource ds) throws SQLException {
//        return EncryptDataSourceFactory.createDataSource(ds, getEncryptRuleConfiguration(), new Properties());
//    }
//
//    private EncryptRuleConfiguration getEncryptRuleConfiguration() {
//        Properties props = new Properties();
//        props.setProperty("aes.key.value", aeskey);
//        EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("AES", props);
//
//        EncryptRuleConfiguration encryptRuleConfig = new EncryptRuleConfiguration();
//        encryptRuleConfig.getEncryptors().put("aes", encryptorConfig);
//
//        log.info("脱敏配置构建完成:{} ", encryptRuleConfig);
//        return encryptRuleConfig;
//    }
//
//}
