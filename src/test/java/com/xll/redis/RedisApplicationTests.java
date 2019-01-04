package com.xll.redis;

import com.xll.redis.bean.User;
import com.xll.redis.config.ThreadConfig;
import com.xll.redis.service.UserService;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class RedisApplicationTests {
    @Autowired
    ThreadConfig config;
    @Autowired
    UserService userService;
    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void contextLoads() throws InterruptedException {/*
        Executor executor = config.getAsyncExecutor();
        for (int i = 0; i < 100; i++) {
            executor.execute(new MyThread());
        }
        Thread.sleep(100);
        log.info("************");
        countDownLatch.countDown();*/
    }
/*
    class MyThread implements Runnable{
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + ":开始准备");
            try {
                countDownLatch.await();
                log.info(Thread.currentThread().getName() + ":开始执行");
                User user = userService.getUser(1L);
                if(user != null){
                    log.info(user.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }*/
}
