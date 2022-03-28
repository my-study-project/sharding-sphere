package com.js.config.table;

import com.alibaba.druid.pool.DruidDataSource;
import com.js.config.common.UserShardingAlgorithm;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Value("${datasource0.url}")
    private String url0;
    @Value("${datasource0.username}")
    private String username0;
    @Value("${datasource0.password}")
    private String password0;
    @Value("${datasource0.driver-class-name}")
    private String driverClassName0;

    @Value(("${spring.datasource.druid.filters}"))
    private String filters;

    @Bean("dataSource")
    public DataSource dataSource() {
        try {
            DruidDataSource dataSource0 = new DruidDataSource();
            dataSource0.setDriverClassName(this.driverClassName0);
            dataSource0.setUrl(this.url0);
            dataSource0.setUsername(this.username0);
            dataSource0.setPassword(this.password0);
            dataSource0.setFilters(this.filters);
            //分库设置
            Map<String, DataSource> dataSourceMap = new HashMap<>(2);
            //添加数据库database0
            dataSourceMap.put("ds0", dataSource0);
            // 配置 t_user 表规则
            TableRuleConfiguration userRuleConfiguration = new TableRuleConfiguration("t_user", "ds0.t_user${0..1}");
            //此处可以自定义
            //userRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_user${id % 2}"));
            StandardShardingStrategyConfiguration standardShardingStrategyConfiguration =
                    new StandardShardingStrategyConfiguration("id", UserShardingAlgorithm.tableShardingAlgorithm);
            userRuleConfiguration.setTableShardingStrategyConfig(standardShardingStrategyConfiguration);
            //主键
            userRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "id", getProperties()));
            // Sharding全局配置
            ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
            shardingRuleConfiguration.getTableRuleConfigs().add(userRuleConfiguration);
            // 创建数据源
            DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new Properties());
            return dataSource;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Properties getProperties() {
        Properties result = new Properties();
        result.setProperty("worker.id", "123");
        return result;
    }
}