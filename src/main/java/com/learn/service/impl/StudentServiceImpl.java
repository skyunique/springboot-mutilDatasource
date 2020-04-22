package com.learn.service.impl;

import com.learn.dao.BaseDao;
import com.learn.dao.cluster.StudentDao;
import com.learn.pojo.Student;
import com.learn.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    protected BaseDao<Student> getMapper() {
        return this.studentDao;
    }
}
