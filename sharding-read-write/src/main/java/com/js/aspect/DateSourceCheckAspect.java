//package com.js.aspect;
//
//import org.apache.shardingsphere.api.hint.HintManager;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.PatternMatchUtils;
//
//@Aspect
//@Component
//public class DateSourceCheckAspect {
//
//    @Value("${spring.shardingsphere.datasource.onlyWritePattern}")
//    private String onlyWritePattern;
//
//    /**
//     * @return
//     * @Description: 设置所有的Service层的方法
//     * @Date: 2021/7/18 5:20 下午
//     */
//
//    @Pointcut("within(com.js.service.*Service.*) || within(com.js.service.*ServiceImpl.*)")
//    public void checkDateSource() {
//
//    }
//
//    /**
//     * @return
//     * @Description: 执行数据库主库切换
//     * @Date: 2021/7/18 5:22 下午
//     */
//
//    @Around(value = "checkDateSource()")
//    public void processAuthority(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("EXECUTION welcome");
//        System.out.println("EXECUTION 调用方法:" + point.getSignature().getName());
//        System.out.println("EXECUTION 目标对象：" + point.getTarget());
//        System.out.println("EXECUTION 首个参数：" + point.getArgs()[0]);
//        String methodName = point.getSignature().getName();
//        if (checkMethod(methodName)) {
//            HintManager.getInstance().setMasterRouteOnly();
//        }
//        point.proceed();
//        System.out.println("EXECUTION success");
//        HintManager.getInstance().close();
//
//
//    }
//
//    private Boolean checkMethod(String methodName) {
//        if (methodName == null || "".equals(methodName)) {
//            return false;
//        }
//        return PatternMatchUtils.simpleMatch(onlyWritePattern.trim().split(","), methodName);
//    }
//
//}
