package com.xll.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableCaching
public class RedisApplication {
    private static ApplicationContext ctx;
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
        /*ctx = SpringApplication.run(RedisApplication.class, args);

        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            TomcatServletWebServerFactory tomcatServletWebServerFactory= (TomcatServletWebServerFactory) ctx.getBean("tomcatServletWebServerFactory");
            int port = tomcatServletWebServerFactory.getPort();
            String contextPath = tomcatServletWebServerFactory.getContextPath();
            System.out.println("http://"+host+":"+port+contextPath+"/");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/

    }
}
