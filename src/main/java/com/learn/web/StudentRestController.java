package com.learn.web;

import com.learn.pojo.Student;
import com.learn.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/stu",method = RequestMethod.POST)
    public boolean insert(@RequestBody Student stu){
        System.out.println("开始新增...");
        return studentService.insert(stu);
    }

    @RequestMapping(value = "/stu",method = RequestMethod.PUT)
    public boolean update(@RequestBody Student student){
        System.out.println("开始更新...");
        return studentService.update(student);
    }

    @RequestMapping(value = "/stu",method = RequestMethod.DELETE)
    public boolean delete(@RequestBody Student student){
        System.out.println("开始删除...");
        return studentService.delete(student);
    }

    @RequestMapping(value = "/stu",method = RequestMethod.GET)
    public List<Student> findByStu(Student student){
        System.out.println("开始查询...");
        return studentService.findByListEntity(student);
    }

    @RequestMapping(value = "/stuBatch",method = RequestMethod.POST)
    public boolean insert(@RequestBody List<Student> studentList){
        return studentService.insertBatch(studentList);
    }

}
