package com.js.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurableAdvisorConfig {

    @Value("${application.basePackage}")
    private String basePackage;

    @Bean
    public AspectJExpressionPointcutAdvisor configurabledvisor() {
        String serviePackage = "execution(* " + basePackage + ".service.*.*(..))";

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(serviePackage);
        advisor.setAdvice(new DateSourceCheckAdvice());
        return advisor;
    }
}
