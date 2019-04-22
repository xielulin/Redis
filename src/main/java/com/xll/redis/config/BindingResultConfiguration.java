package com.xll.redis.config;

import com.xll.redis.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;

/**
 * @author xielulin
 * @create 2018-12-12 10:24
 * @desc 异常拦截
 **/
@Aspect
@Configuration
public class BindingResultConfiguration {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {}

    @Pointcut("execution(* com.xll.redis.controller.*Controller.*(..))")
    public void methodPointcut() {}

    @Around("requestMapping() && methodPointcut()")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (ObjectUtils.isEmpty(args)) {
            return joinPoint.proceed();
        }
        BindingResult bindingResult = getBindingResult(args);
        if(bindingResult != null && bindingResult.hasErrors()) {
          String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
//            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return Result.warn(message);
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

    public BindingResult getBindingResult(Object[] args){
        for (Object arg : args){
            if(arg instanceof BindingResult){
                return (BindingResult) arg;
            }
        }
        return null;
    }
}

