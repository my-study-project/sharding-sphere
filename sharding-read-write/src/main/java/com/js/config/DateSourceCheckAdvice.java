package com.js.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Component
@Slf4j
public class DateSourceCheckAdvice implements MethodInterceptor {

    @Value("${spring.shardingsphere.datasource.onlyWritePattern}")
    private String onlyWritePattern;

    @Override
    public Object invoke(MethodInvocation invocation) {
        Object result = null;
        try {
            String methodName = invocation.getMethod().getName();
            if (checkMethod(methodName)) {
                HintManager.getInstance().setMasterRouteOnly();
            } else {
                HintManager.getInstance().setDatabaseShardingValue("read");
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
