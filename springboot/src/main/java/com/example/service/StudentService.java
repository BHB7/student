package com.example.service;

import com.example.common.PageResult;
import com.example.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * (Student)表服务接口
 *
 * @author 7z
 * @since 2024-05-24 11:50:13
 */
public interface StudentService {
    List<Student> studentList();

    boolean delete(Integer sid);

    boolean add(Student student);

    boolean update(Student student);
   PageResult studentListPage(Integer page, Integer size);

    PageResult listP(Map<String, Object> params, int pageNum, int pageSize);
}
