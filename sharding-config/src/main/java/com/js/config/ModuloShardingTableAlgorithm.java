package com.js.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public final class ModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {

    /**
     * 选表
     */
    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<Long> shardingValue) {

        log.info("tableNames:{}", JSON.toJSONString(tableNames));
        log.info("shardingValue:{}", JSON.toJSONString(shardingValue));

        String tableName = "";

        if ("t_user".equalsIgnoreCase(shardingValue.getLogicTableName())) {
            for (String each : tableNames) {
                if (each.endsWith(shardingValue.getValue() % tableNames.size() + "")) {
                    log.info("table:{}", each);
                    tableName = each;
                    break;
                }
            }
        }

        // 分库分表
        if ("t_order".equalsIgnoreCase(shardingValue.getLogicTableName()) ||
                "t_order_item".equalsIgnoreCase(shardingValue.getLogicTableName())) {
            for (String each : tableNames) {
                if (each.endsWith(shardingValue.getValue() % tableNames.size() + "")) {
                    tableName = each;
                    break;
                }
            }
        }

        log.info("tableName:{}", tableName);
        if (StringUtils.isNotEmpty(tableName)) {
            return tableName;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * 实现between and查询
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        log.info("collection:{}", JSON.toJSONString(collection));
        log.info("rangeShardingValue:{}", JSON.toJSONString(rangeShardingValue));

        Collection<String> collect = new ArrayList<>();
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            for (String each : collection) {
                if (each.endsWith(i % collection.size() + "")) {
                    collect.add(each);
                }
            }
        }

        return collect;
    }
}
