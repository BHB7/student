package com.example.service.impl;


import com.example.common.PageResult;
import com.example.entity.Student;

import com.example.mapper.StudentMapper;
import com.example.service.StudentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

/**
 * (Student)表服务实现类
 *
 * @author makejava
 * @since 2024-05-24 11:50:13
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Student> studentList() {
        return studentMapper.studentList();
    }

    /*
    * 删除
    * */
    @Override
    public boolean delete(Integer id) {
        System.out.println(id);
        boolean delete = studentMapper.delete(id);
        return delete;
    }
    /**
     * 添加
     */
    @Override
    public boolean add(Student student) {
        boolean add = studentMapper.add(student);
        return add;
    }
    /**
     * 编辑
     * */
    @Override
    public boolean update(Student student) {
        if (studentMapper.update(student)){
            return true;
        }
        return false;
    }

    /*
    * 分页
    * */
    @Override
    public PageResult studentListPage(Integer pageNow, Integer pageSize) {
        PageHelper.startPage(pageNow,pageSize);
        List<Student> studentList = studentMapper.studentListPage();
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        /*
        * {
        *   total: 10,
        *   list:{
        *   }
        * }
        * */
        return PageResult.success((int) pageInfo.getTotal(),pageInfo.getList());
    }

    /*
    *
    * 模糊查询
    * */
    @Override
    public PageResult listP(Map<String, Object> params, int pageNow, int pageSize) {
        PageHelper.startPage(pageNow, pageSize);
        List<Student> list = studentMapper.findByParams(params);
        return PageResult.success((int) new PageInfo<>(list).getTotal(), list);
    }


}
