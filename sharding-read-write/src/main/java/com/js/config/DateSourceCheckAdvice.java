package com.js.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Component
public class DateSourceCheckAdvice implements MethodInterceptor {

    @Value("${spring.shardingsphere.datasource.onlyWritePattern}")
    private String onlyWritePattern;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        if (checkMethod(methodName)) {
            HintManager.getInstance().setMasterRouteOnly();
        }
        Object result = invocation.proceed();
        HintManager.getInstance().close();
        return result;
    }

    private Boolean checkMethod(String methodName) {
        if (methodName == null || "".equals(methodName)) {
            return false;
        }
        return PatternMatchUtils.simpleMatch(onlyWritePattern.trim().split(","), methodName);
    }
}
