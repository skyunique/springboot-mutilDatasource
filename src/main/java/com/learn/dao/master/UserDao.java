package com.learn.dao.master;

import com.learn.dao.BaseDao;
import com.learn.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseDao<User> {
}
