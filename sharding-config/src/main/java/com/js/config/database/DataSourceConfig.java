package com.js.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.js.config.common.UserShardingAlgorithm;
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

    @Value("${datasource1.url}")
    private String url1;
    @Value("${datasource1.username}")
    private String username1;
    @Value("${datasource1.password}")
    private String password1;
    @Value("${datasource1.driver-class-name}")
    private String driverClassName1;

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
            DruidDataSource dataSource1 = new DruidDataSource();
            dataSource1.setDriverClassName(this.driverClassName1);
            dataSource1.setUrl(this.url1);
            dataSource1.setUsername(this.username1);
            dataSource1.setPassword(this.password1);
            dataSource1.setFilters(this.filters);
            //分库设置
            Map<String, DataSource> dataSourceMap = new HashMap<>(2);
            //添加两个数据库database0和database1
            dataSourceMap.put("ds0", dataSource0);
            dataSourceMap.put("ds1", dataSource1);
            // 配置 t_user 表规则
            TableRuleConfiguration userRuleConfiguration = new TableRuleConfiguration("t_user", "ds${0..1}.t_user0");
            //id 是 偶数的都插入到了 test0 库的 t_user0 表中， 奇数的都插入到了 test1 库中的 t_user1 表中
            /**
             * #表策略 行表达式分表规则
             * sharding.jdbc.config.sharding.tables.user.actual-data-nodes=ds_$->{0..1}.user_$->{0..4}
             * sharding.jdbc.config.sharding.tables.user.table-strategy.inline.sharding-column=id
             * sharding.jdbc.config.sharding.tables.user.table-strategy.inline.algorithm-expression=user_$->{id % 5}
             * sharding.jdbc.config.sharding.tables.user.key-generator-column-name=id
             */
            // 行表达式分库规则
            /**
             * #库策略
             * sharding.jdbc.config.sharding.default-database-strategy.inline.sharding-column=id
             * harding.jdbc.config.sharding.default-database-strategy.inline.algorithm-expression=ds_$->{id % 2}
             */
            StandardShardingStrategyConfiguration standardShardingStrategyConfiguration =
                    new StandardShardingStrategyConfiguration("id", UserShardingAlgorithm.databaseShardingAlgorithm);
            userRuleConfiguration.setDatabaseShardingStrategyConfig(standardShardingStrategyConfiguration);
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
}