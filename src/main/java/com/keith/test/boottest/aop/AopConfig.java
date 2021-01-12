package com.keith.test.boottest.aop;

import com.alibaba.fastjson.JSON;
import com.keith.test.boottest.annotation.ForAopAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面配置
 *
 * @author keith
 * @since 2021-01-11
 */
@Aspect
@Component
@Slf4j
public class AopConfig {

    @Pointcut("execution(public * com.keith.test.boottest.controller..*(..))")
    public void matchCondition() {}

    @Pointcut("@annotation(com.keith.test.boottest.annotation.ForAopAnnotation)")
    public void annotationCondition() {}

    @Around("annotationCondition()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = joinPoint.getThis().getClass().getMethod(joinPoint.getSignature().getName());
        log.info("成功获取到注解", method.getAnnotation(ForAopAnnotation.class));
        log.info("aop成功");
        Object[] args = joinPoint.getArgs();
        log.info("args：{}", JSON.toJSONString(args));
        Object result = joinPoint.proceed(args);
        log.info("result:{}", JSON.toJSONString(result));
    }
}
