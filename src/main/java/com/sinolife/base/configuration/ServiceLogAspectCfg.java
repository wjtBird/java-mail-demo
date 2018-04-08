package com.sinolife.base.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by wjt on 2018/4/2.
 * @author wjt
 */
@Component
@Aspect
@Order(6)
public class ServiceLogAspectCfg {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.sinolife.mailbusiness.service..*.*(..))")
    public void parameters() {}


    @Before("parameters()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        logger.info(joinPoint.getClass().getName());
        logger.info("METHOD NAME:{},METHOD ARGS:{}",method,joinPoint.getArgs());
        logger.info("TARGET CLASS NAME:{}",joinPoint.getTarget().getClass());
    }

    @AfterReturning(returning = "ret", pointcut = "parameters()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

}
