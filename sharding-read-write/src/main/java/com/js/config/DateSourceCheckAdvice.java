package com.js.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.yaml.config.masterslave.YamlMasterSlaveRuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.List;

@Component
@Slf4j
public class DateSourceCheckAdvice implements MethodInterceptor {

    @Value("${spring.shardingsphere.datasource.onlyWritePattern}")
    private String onlyWritePattern;

    @Autowired
    private YamlMasterSlaveRuleConfiguration yamlMasterSlaveRuleConfiguration;

    @Override
    public Object invoke(MethodInvocation invocation) {
        Object result = null;
        try {
            String methodName = invocation.getMethod().getName();
            if (checkMethod(methodName)) {
                HintManager.getInstance().setMasterRouteOnly();
            } else {
                // 读库选择随机到一个从库上
                List<String> slaveDataSourceNames = yamlMasterSlaveRuleConfiguration.getSlaveDataSourceNames();
                int i = RandomUtils.nextInt(0, slaveDataSourceNames.size());
                HintManager.getInstance().setDatabaseShardingValue(slaveDataSourceNames.get(i));
            }
            result = invocation.proceed();
        } catch (Exception e) {
            log.error("当前方法执行出现异常", e);
        } finally {
            if (HintManager.isMasterRouteOnly()) {
                HintManager.getInstance().close();
            }
            return result;
        }

    }

    private Boolean checkMethod(String methodName) {
        if (methodName == null || "".equals(methodName) || StringUtils.isBlank(onlyWritePattern)) {
            return false;
        }
        return PatternMatchUtils.simpleMatch(onlyWritePattern.trim().split(","), methodName);
    }
}
