package com.js.aspect;

import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Aspect
@Component
public class DateSourceCheck {

    @Value("${spring.shardingsphere.datasource.onlyWritePattern}")
    private String onlyWritePattern;

    /**
     * @return
     * @Description: 设置所有的Service层的方法
     * @Date: 2021/7/18 5:20 下午
     */
    @Pointcut("within(com.js.service.*Service.*) || within(com.js.service.*ServiceImpl.*)")
    public void checkDateSource() {

    }

    /**
     * @return
     * @Description: 执行数据库主库切换
     * @Date: 2021/7/18 5:22 下午
     */
    @Before("checkDateSource()")
    public void beforelogin(ProceedingJoinPoint point) {
        String methodName = point.getSignature().getName();
        if (checkMethod(methodName)) {
            HintManager hintManager = HintManager.getInstance();
            hintManager.setMasterRouteOnly();
        }
    }

    @After("checkDateSource()")
    public void afterlogin() {
        System.out.println("after");
    }

    private Boolean checkMethod(String methodName) {
        if (methodName == null || "".equals(methodName)) {
            return false;
        }
        return PatternMatchUtils.simpleMatch(onlyWritePattern.trim().split(","), methodName);
    }

}
