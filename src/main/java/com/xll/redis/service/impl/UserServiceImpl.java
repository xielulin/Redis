package com.xll.redis.service.impl;

import com.xll.redis.bean.User;
import com.xll.redis.constants.Constant;
import com.xll.redis.dao.UserRepository;
import com.xll.redis.exception.BaseException;
import com.xll.redis.param.AddOrUpdataUserParam;
import com.xll.redis.param.GetByNameParam;
import com.xll.redis.result.Result;
import com.xll.redis.service.UserService;
import com.xll.redis.utils.DateUtil;
import lombok.extern.java.Log;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Autowired
    CacheManager cacheManager;

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
    @Cacheable(value="users", key="args[0]",unless = "#result == null ")
    public User getUser(Long id) {
        User user = userRepository.getByIdAndDelIsFalse(id);
        return user;
    }

    @Override
    @CachePut(value="users", key="#result.id")
    public User addUser(AddOrUpdataUserParam param) {
        User user = new User();
        user.setAge(param.getAge());
        user.setName(param.getName());
        user.setSex(param.getSex());
        user= userRepository.saveAndFlush(user);
        String date = DateUtil.getText(DateUtil.DateFormat.DF_YYYY_MM_DD_HH_MM);
        log.info(date+": user添加成功！");
        return user;
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

    @Override
    @CacheEvict(value="users", key="args[0]")
    public void deleteUser(Long id) {
        User user = getUser(id);
        if(user == null){
            throw new BaseException("删除user失败，该id不存在");
        }
        user.setDel(true);
        userRepository.save(user);
    }

    @Override
    public Result<List<User>> getByName(GetByNameParam param) {
        Result<List<User>> result = new Result<>();
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(criteriaBuilder.like(root.get("name"), "%"+param.getName()+"%"));
                predicate.add(criteriaBuilder.isFalse(root.get("del")));
                return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
            }
        };

        Page<User> users = userRepository.findAll(specification, PageRequest.of(param.getCurrentPage(), param.getPageSize()));
        result.setData(users.getContent());
        long tatol = userRepository.count(specification);
        if(tatol == 0){
            return Result.ok();
        }
        long totalPage = tatol%param.getPageSize() == 0?tatol/param.getPageSize():tatol/param.getPageSize()+1;
        if(param.getCurrentPage()+1>totalPage){
            throw new BaseException("currentPage错误，请重新输入！");
        }
        result.setTotal(tatol);
        result.setPageSize(param.getPageSize());
        result.setCurrentPage(param.getCurrentPage());
        result.setTotalPage(totalPage);
        result.setStatus(Constant.ResultConstant.SUCCESS);
        return result;
    }


}
