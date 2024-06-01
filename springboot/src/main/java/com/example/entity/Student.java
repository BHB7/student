package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author 7z
 * @since 2024-05-24 11:51:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = 192300745783986238L;
    
    private Integer id;
    /**
     * 学号
     */
    private String sid;
    /**
     * 学生名字
     */
    private String sname;
    /**
     * 学生性别 0男 1女
     */
    private String ssex;
    /**
     * 班级
     */
    private String sclass;
    /**
     * 学生密码
     */
    private String spwd;


}

