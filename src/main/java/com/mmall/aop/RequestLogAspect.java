package com.mmall.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


@Aspect //这是一个切面
@Component //托管到spring ioc 管理
public class RequestLogAspect {
    private static final Logger logger
            = LoggerFactory.getLogger(RequestLogAspect.class);
    @Pointcut("execution(public * com.mmall.controller.portal.*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("doBefore(JoinPoint joinPoint)............");
        //接受到请求，记录请求内容
        ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request =attr.getRequest();
        logger.info("URL:"+request.getRequestURI().toString());
        logger.info("IP:"+request.getRemoteAddr());
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret){
        System.out.println("doAfterReturning().....");
        //处理完请求返回内容
        logger.info("RESPONSE:"+ret.toString());
    }




}
