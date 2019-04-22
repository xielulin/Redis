package com.xll.redis.dao;

import com.xll.redis.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * @author xielulin
 * @create 2018-11-18 11:59
 * @desc UserDao
 **/
@Component
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    User getByIdAndDelIsFalse(Long id);
}
