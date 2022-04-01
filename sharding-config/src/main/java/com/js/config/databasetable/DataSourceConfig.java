//package com.js.config.databasetable;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
//import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Value("${datasource0.url}")
//    private String url0;
//    @Value("${datasource0.username}")
//    private String username0;
//    @Value("${datasource0.password}")
//    private String password0;
//    @Value("${datasource0.driver-class-name}")
//    private String driverClassName0;
//
//    @Value("${datasource1.url}")
//    private String url1;
//    @Value("${datasource1.username}")
//    private String username1;
//    @Value("${datasource1.password}")
//    private String password1;
//    @Value("${datasource1.driver-class-name}")
//    private String driverClassName1;
//
//    @Value(("${spring.datasource.druid.filters}"))
//    private String filters;
//
//    @Bean("dataSource")
//    public DataSource dataSource() {
//        try {
//            DruidDataSource dataSource0 = new DruidDataSource();
//            dataSource0.setDriverClassName(this.driverClassName0);
//            dataSource0.setUrl(this.url0);
//            dataSource0.setUsername(this.username0);
//            dataSource0.setPassword(this.password0);
//            dataSource0.setFilters(this.filters);
//
//            DruidDataSource dataSource1 = new DruidDataSource();
//            dataSource1.setDriverClassName(this.driverClassName1);
//            dataSource1.setUrl(this.url1);
//            dataSource1.setUsername(this.username1);
//            dataSource1.setPassword(this.password1);
//            dataSource1.setFilters(this.filters);
//            //分库设置
//            Map<String, DataSource> dataSourceMap = new HashMap<>(2);
//            //添加两个数据库database0和database1
//            dataSourceMap.put("ds0", dataSource0);
//            dataSourceMap.put("ds1", dataSource1);
//            /**
//             * 除了基本的分库分表规则以外，还有一些其他的配置，比如绑定表。这里先不一一举例了，参照官方文档配即可。
//             * 　　举个例子：现在有 order, order_detail两张表，1:1的关系。
//             * 　　在配置的时候，应该将相同 order_id 的 order 记录 和 order_detail 记录 映射到相同尾号的表中。这样方便连接查询。比如都插入到  order0, order_detail0中。
//             * 　　如果配置了绑定关系，那么只会产生一条查询 select * from order0 as o join order_detail0 as d  on o.order_id = d.order_id。
//             * 　　否则会产生笛卡儿积查询，
//             * 　　　　select * from order0 as o join order_detail0 as d  on o.order_id = d.order_id。
//             * 　　　　select * from order0 as o join order_detail1 as d  on o.order_id = d.order_id。
//             * 　　　　select * from order1 as o join order_detail0 as d  on o.order_id = d.order_id。
//             * 　　　　select * from order1 as o join order_detail1 as d  on o.order_id = d.order_id。
//             */
//            // 配置 t_user 表规则 库：test0 test1 表 t_user1 t_user0
//            TableRuleConfiguration userRuleConfiguration = new TableRuleConfiguration("t_user", "ds${0..1}.t_user${0..1}");
//            //id 是 偶数的都插入到了 test0 库的 t_user0 表中， 奇数的都插入到了 test1 库中的 t_user1 表中
//            /**
//             * #表策略 行表达式分表规则
//             * sharding.jdbc.config.sharding.tables.user.actual-data-nodes=ds_$->{0..1}.user_$->{0..1}
//             * sharding.jdbc.config.sharding.tables.user.table-strategy.inline.sharding-column=id
//             * sharding.jdbc.config.sharding.tables.user.table-strategy.inline.algorithm-expression=user_$->{id % 5}
//             * sharding.jdbc.config.sharding.tables.user.key-generator-column-name=id
//             */
//            //sharding.jdbc.config.sharding.tables.user.table-strategy.standard.precise-algorithm-class-name: com.forezp.sharedingjdbcmasterslavetables.MyPreciseShardingAlgorithm
//            //此处可以自定义
//            userRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_user${id % 2}"));
//            // 行表达式分库规则
//            /**
//             * #库策略
//             * sharding.jdbc.config.sharding.default-database-strategy.inline.sharding-column=id
//             * harding.jdbc.config.sharding.default-database-strategy.inline.algorithm-expression=ds_$->{id % 2}
//             */
//            //根据城市分库
//            userRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("city_id", "ds${city_id % 2}"));
//            // Sharding全局配置
//            ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
//            shardingRuleConfiguration.getTableRuleConfigs().add(userRuleConfiguration);
//            // 创建数据源
//            DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new Properties());
//            return dataSource;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//}