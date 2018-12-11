package com.xll.redis.service.impl;

import com.xll.redis.bean.User;
import com.xll.redis.dao.UserRepository;
import com.xll.redis.param.AddOrUpdataUserParam;
import com.xll.redis.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author xielulin
 * @create 2018-11-18 11:57
 * @desc UserService
 **/
@Service
@Log
public class UserServiceImpl  implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String REDIS_KEY = "User";


    @Cacheable(value="users", key="#id")
    public User getUsers(Long id) {
        ValueOperations <String,User> valueOperations = redisTemplate.opsForValue();
        User user = valueOperations.get(id.toString());
        String date = DateFormatUtils.format(new Date(),"yy-mm-dd hh:mm:ss");
        if(user != null){
            log.info(date+": 从redis中获取User");
            return user;
        }else {
            Optional<User> optional = userRepository.findById(id);
            if(optional.isPresent()){
                user = optional.get();
                valueOperations.set(user.getId().toString(),user);
                log.info(date+":无缓存的时候调用");
                log.info(date+": 新增user入redis");
                return user;
            } else{
                return null;
            }
        }

    }

    @Override
    @Cacheable(value="users", key="args[0]")
    public User getUser(Long id) {
        ValueOperations <String,User> valueOperations = redisTemplate.opsForValue();
        String date = DateFormatUtils.format(new Date(),"yy-mm-dd hh:mm:ss");
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            User user = optional.get();
            //valueOperations.set(user.getId().toString(),user);
            log.info(date+":无缓存的时候调用");
            return user;
        } else {
            return null;
        }

    }

    @Override
//    @CachePut(value="users", key="args[0].id")
    public void addUser(AddOrUpdataUserParam param) {
        User user = new User();
        user.setAge(param.getAge());
        user.setName(param.getName());
        user.setSex(param.getSex());
        userRepository.save(user);
        String date = DateFormatUtils.format(new Date(),null);
        log.info(date+": 新增user入redis成功");
    }

    @Override
    @CachePut(value="users", key="args[0].id")
    public User updateUser(AddOrUpdataUserParam param) {
        User user = getUser(param.getId());
        user.setAge(param.getAge());
        user.setName(param.getName());
        user.setSex(param.getSex());
        return userRepository.saveAndFlush(user);
    }
}
