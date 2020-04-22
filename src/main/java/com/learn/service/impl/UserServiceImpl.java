package com.learn.service.impl;

import com.learn.dao.BaseDao;
import com.learn.dao.master.UserDao;
import com.learn.pojo.User;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    protected BaseDao<User> getMapper() {
        return this.userDao;
    }
}
