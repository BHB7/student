package com.example.service;


import com.example.entity.Admin;
import com.example.entity.Mclasses;

/**
 * (Admin)表服务接口
 *
 * @author 7z
 * @since 2024-05-24 11:55:36
 */
public interface AdminService {
    Admin login(String aid, String apwd);

    Admin register(String aname, String apwd);

    /* 填充图片地址 */
    Integer addImgPath(String s, String userId);

    boolean edit(String aid, String aname, String adesc);
}
