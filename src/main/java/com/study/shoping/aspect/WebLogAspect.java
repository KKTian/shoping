package com.study.shoping.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.CoyoteOutputStream;
import org.apache.catalina.connector.OutputBuffer;
import org.apache.tomcat.util.buf.ByteChunk;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 描述: Controller reqeust/response日志拦截类
 *
 * @outhor gaobitian
 * @create 2018-05-29 13:47
 */

@Aspect
@Order(5)
@Component
@Slf4j
public class WebLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.study.shoping.*.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容
        log.debug("url : {}", request.getRequestURL().toString());
        log.debug("http_method : {}", request.getMethod());
        log.debug("ip : {}", request.getRemoteAddr());
        log.debug("class : {}, method : {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.debug("args : {}", Arrays.toString(joinPoint.getArgs()));


    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();


//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Method", "POST,GET");


        // 处理完请求，返回内容
//        System.out.println(strRe);
        log.debug("response status:{}",response.getStatus());

        log.debug("response : {}",ret.toString());
        log.debug("spend time : {}", (System.currentTimeMillis() - startTime.get()));
    }
}