package com.example.controller;

import com.example.common.PageResult;
import com.example.common.Result;
import com.example.entity.Student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class WebController {

    @Autowired
    private StudentService studentservice;


    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }


    /**
    * 获取所有学生
    * */
    @GetMapping("/list")
    public Result studentList() {
        return Result.success(studentservice.studentList());
    }

    /**
    * 分页获取学生
    * */
    @GetMapping("/listPage")
    public PageResult studentListPage(@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return studentservice.studentListPage(pageNow, pageSize);
    }

    /**
    * 分页模糊查询获取学生
    *
    * */
    @GetMapping("/listP")
    public PageResult listP(@RequestParam(required = false) String sid,
                            @RequestParam(required = false) String sname,
                            @RequestParam(required = false) String sclass,
                            @RequestParam(required = false) String ssex,
                            @RequestParam(value = "pageNow", defaultValue = "1") int pageNow,
                            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        Map<String, Object> params = new HashMap<>();
        if (sid != null) {
            params.put("param1", sid); // 使用 param1 替换原来的 sid
        }
        if (sname != null) {
            params.put("param2", sname); // 使用 param2 替换原来的 sname
        }
        if (sclass != null) {
            params.put("param3", sclass); // 使用 param3 替换原来的 sclass
        }
        if (ssex != null) {
            params.put("param4", ssex); // 使用 param4 替换原来的 ssex
        }

        return studentservice.listP(params, pageNow, pageSize);
    }


    /**
    * 删除学生
    * */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean delete = studentservice.delete(id);
        if (!delete) {
            return Result.error("操作失败");
        }
        return Result.success("操作成功");
    }
    /**
     * 添加学生
     * */
    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        System.out.println(student);
        if (student.getSid() == null || student.getSname() == null || student.getSsex() == null || student.getSclass() == null) {
            return Result.error("参数错误");
        }
        boolean add = studentservice.add(student);
        if (add) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }
    /**
     * 修改学生
     * */
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        System.out.println(student);
        if (student.getId() == null || student.getSid() == null || student.getSname() == null || student.getSsex() == null || student.getSclass() == null) {
            return Result.error("参数错误");
        }
        boolean update = studentservice.update(student);
        if (update) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }
    /**
    * sid 唯一
    * */
    @GetMapping("/only")
    public Result only(@RequestParam String sid) {
        List<Student> studentList = studentservice.studentList();
        for (Student student : studentList) {
            if (student.getSid().equals(sid)) {
            System.out.println("重复");
                return Result.error("学号重复");
            }
        }
        return Result.success("学号可用");
    }

}
