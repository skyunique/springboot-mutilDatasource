package com.learn.dao.cluster;

import com.learn.dao.BaseDao;
import com.learn.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao extends BaseDao<Student> {
}
