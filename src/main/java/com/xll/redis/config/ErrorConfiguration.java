package com.xll.redis.config;

import com.xll.redis.exception.BaseException;
import com.xll.redis.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xielulin
 * @create 2018-12-12 11:05
 * @desc 错误拦截
 **/
@RestControllerAdvice
public class ErrorConfiguration {
        private static final Logger logger = LoggerFactory.getLogger(ErrorConfiguration.class);

        @ResponseStatus(code = HttpStatus.NOT_FOUND)
        @ExceptionHandler(NoHandlerFoundException.class)
        public Object notFound(HttpServletRequest request, Exception e) throws Exception {
            logger.error(e.getMessage());
            return Result.warn(e.getMessage());
        }

        @ExceptionHandler(value = HttpMediaTypeException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public Object getMessage(HttpServletRequest request, Exception e) throws Exception {
            logger.error(e.getMessage());
            return Result.warn(e.getMessage());
        }

        @ExceptionHandler(value = BaseException.class)
        @ResponseStatus(HttpStatus.OK)
        public Object bizException(Exception e) throws Exception {
            logger.error(e.getMessage());
            return Result.warn(e.getMessage());
        }

        @ExceptionHandler(value = Exception.class)
        @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
        public Object jsonErrorHandler(HttpServletRequest request, Exception e) throws Exception {
            logger.error(e.getMessage());
            return Result.warn(e.getMessage());
        }
    }

