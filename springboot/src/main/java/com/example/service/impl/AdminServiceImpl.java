package com.example.service.impl;

import com.example.entity.Admin;
import com.example.entity.Mclasses;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2024-05-24 11:55:36
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(String aid , String apwd) {
        try {
            // 输入验证
            if (aid == null || apwd == null) {
                // 对于空或不合法的输入，可以记录日志，并返回登录失败
                System.out.println("登录失败：管理员信息不能为空或不合法");
                return null;
            }

            Admin loginResult = adminMapper.login(aid, apwd);
            loginResult.setApwd(null);
            // 根据实际结果返回登录状态
            return loginResult;
        } catch (Exception e) {
            // 异常处理，记录日志
            System.out.println("登录异常：" + e.getMessage());
            // 根据实际情况，异常情况下可能需要返回false或抛出自定义异常
            return null;
        }
    }

    // 注册
    @Override
    public Admin register(String aname, String apwd) {
        if (aname != null && apwd != null) {
            // 随机生成管理员ID 8位
            String aid = (int) (Math.random() * LocalDateTime.now().getNano()) + 10000000 + "";
            // 查询是否已存在该管理员
            if (adminMapper.sole(aid).size() > 0) {
                // 如果已存在，重新生成ID
               aid = (int) (Math.random() * LocalDateTime.now().getNano()) + 10000000 + "";
            }
            System.out.println(LocalDateTime.now().getNano());

            if( adminMapper.register(aid, aname, apwd)){
                Admin admin = new Admin();
                admin.setAid(aid);
                admin.setAname(aname);
                return admin;
            }
        }
        return null;
    }

    /* 填充图片url */
    @Override
    public Integer addImgPath(String imgPath, String userId) {
        return adminMapper.addImgPath(imgPath,userId);
    }

    /* 修改管理员信息 */
    @Override
    public boolean edit(String aid, String aname, String adesc) {
        if (aid != null && aname != null && adesc != null) {
            return adminMapper.edit(aid, aname, adesc);
        }
        return false;
    }
}
