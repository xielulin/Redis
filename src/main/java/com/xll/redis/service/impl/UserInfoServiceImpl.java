package com.xll.redis.service.impl;

import com.xll.redis.bean.UserInfo;
import com.xll.redis.constants.Constant;
import com.xll.redis.dao.UserInfoRepository;
import com.xll.redis.dto.UserInfoDto;
import com.xll.redis.exception.BaseException;
import com.xll.redis.param.AddOrUpdataUserInfoParam;
import com.xll.redis.param.GetUserInfosParam;
import com.xll.redis.result.PageResult;
import com.xll.redis.result.Result;
import com.xll.redis.service.UserInfoService;
import lombok.extern.java.Log;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author xielulin
 * @create 2018-12-21 10:19
 * @desc UserInfoServiceImpl
 **/
@Service
@Log
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoRepository userInfoRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CacheManager cacheManager;

    @Override
    @Cacheable(value="userInfo", key="args[0]",unless = "#result == null ")
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public Result<List<UserInfoDto>> getUserList(GetUserInfosParam param) {
        Result<List<UserInfoDto>> result = new PageResult();
        Specification<UserInfo> specification = new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                predicate.add(criteriaBuilder.like(root.get("name"), "%"+param.getName()+"%"));
                return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
            }
        };
        long tatol = userInfoRepository.count(specification);
        if(tatol == 0){
            return PageResult.ok("没有用户");
        }
        long totalPage = tatol%param.getPageSize() == 0?tatol/param.getPageSize():tatol/param.getPageSize()+1;
        if(param.getCurrentPage()+1>totalPage){
            throw new BaseException("currentPage错误，请重新输入！");
        }
        //list.stream().map(xml -> new XmlNameListableVo(xml.getXmlId(), xml.getXmlName(), xml.getEnable()))
        //                .collect(Collectors.toList());
        Page<UserInfo> userInfos = userInfoRepository.findAll(specification, PageRequest.of(param.getCurrentPage(), param.getPageSize(), Sort.by("uid").descending()));
        List<UserInfo> content = userInfos.getContent();
        List<UserInfoDto> collect = content.stream().map(userInfo -> new UserInfoDto(userInfo.getUid(), userInfo.getUsername(), userInfo.getName())).collect(Collectors.toList());
        result.setData(collect);

        ((PageResult) result).setTotal(tatol);
        ((PageResult) result).setPageSize(param.getPageSize());
        ((PageResult) result).setCurrentPage(param.getCurrentPage());
        ((PageResult) result).setTotalPage(totalPage);
        result.setStatus(Constant.ResultConstant.SUCCESS);
        return result;
    }

    @Override
    @CachePut(value="userInfo", key="#result.uid")
    public UserInfo addUserInfo(AddOrUpdataUserInfoParam param) {
        try {
            //1.从redis缓存查询
            ValueOperations<String,UserInfo> valueOperations = redisTemplate.opsForValue();
            UserInfo userInfo = valueOperations.get(param.getName());
            if(userInfo != null){
                throw new BaseException("该昵称已存在，请重新输入！");
            }
            //2.从数据库查询
            UserInfo userInfos = userInfoRepository.findByUsername(param.getUsername());
            if(userInfos != null){
                throw new BaseException("该昵称已存在，请重新输入！");
            }
            String salt = UUID.randomUUID().toString();
            UserInfo newUserInfo = new UserInfo();
            newUserInfo.setName(param.getName());
            newUserInfo.setSalt(salt);
            newUserInfo.setState(Constant.UserStatusConstant.UNDELETE);
            newUserInfo.setUsername(param.getUsername());
            int hashCount = 2;
            String realSalt = newUserInfo.getCredentialsSalt();
            String hashAlgorithmName = "MD5";
            SimpleHash simpleHash = new SimpleHash(hashAlgorithmName,param.getPassword(),realSalt,hashCount);
            newUserInfo.setPassword(simpleHash.toHex());
            newUserInfo=userInfoRepository.save(newUserInfo);
            UserInfoDto userInfoDto = new UserInfoDto(newUserInfo.getUid(),newUserInfo.getUsername(),newUserInfo.getName());
            return newUserInfo;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.WARNING,e.getMessage());
            throw new BaseException("添加失败，请联系管理员!");
        }
    }

    @Override
    @Cacheable(value = "userInfo",key = "#id",unless = "#result == null ")
    public UserInfoDto getUserById(Integer id) {
        UserInfo userInfo = userInfoRepository.getOne(id);
        UserInfoDto infoDto = new UserInfoDto(userInfo.getUid(),userInfo.getUsername(),userInfo.getName());
        return infoDto;
    }
}