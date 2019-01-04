package com.xll.redis.dao;

import com.xll.redis.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xielulin
 * @create 2018-12-21 10:20
 * @desc
 **/
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>, JpaSpecificationExecutor<UserInfo> {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}