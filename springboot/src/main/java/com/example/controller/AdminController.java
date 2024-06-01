package com.example.controller;

import com.UpYun;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Mclasses;
import com.example.service.AdminService;
import com.example.service.MclassesService;
import com.upyun.UpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminservice;

    @Autowired
    private MclassesService mclassesService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        String aid = admin.getAid();
        String apwd = admin.getApwd();
        Admin admin1 = adminservice.login(aid, apwd);
        if (admin1 != null) {
            return Result.success(admin1);
        }
        return Result.error("账号或者密码错误");
    }

    /**注册*/
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        String aname = admin.getAname();
        String apwd = admin.getApwd();
        if (aname == null || apwd == null) {
            return Result.error("参数错误");
        }
        Admin admin1 = adminservice.register(aname, apwd);
        if (admin1 != null) {
            return Result.success(admin1);
        }
        return Result.error("注册失败");
    }

    /**
     *获取管理的班级
     * */
    @GetMapping("/getClasses")
    public Result getClasses(@RequestParam(value = "aid", required = false) String aid) {
        if (aid == null) {
            return Result.error("参数错误");
        }
        return Result.success(mclassesService.getClasses(aid));
    }

    /**
     *添加班级
     * */
    @PostMapping("/addClass")
    public Result addClass(@RequestBody Mclasses mclasses) {
        String aid = mclasses.getAid();
        String classes = mclasses.getClasses();
        String cont = mclasses.getCont();
        if (aid == null || classes == null || cont == null) {
            return Result.error("参数错误");
        }
        return mclassesService.addClass(aid, classes, cont) ? Result.success() : Result.error("添加失败");
    }

    /**
     *删除班级
     * */
    @DeleteMapping("/del")
    public Result del(@RequestParam(value = "classes") String classes) {
        System.out.println(classes);
        return mclassesService.delete(classes) ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**唯一班级*/
    @GetMapping("/sole")
    public Result sole(@RequestParam(value = "classes") String classes, @RequestParam(value = "aid") String aid) {
        System.out.println(classes);
        System.out.println(aid);
        return mclassesService.sole(classes, aid) ? Result.error("班级已存在") : Result.success();
    }

    /**统计班级*/
    @GetMapping("/count")
    public Result count() {
        return Result.success(mclassesService.count());
    }

    /**编辑班级*/
    @PutMapping("/classes/edit")
    public Result edit(@RequestBody Mclasses mclasses) {
        String classes = mclasses.getClasses();
        String cont = mclasses.getCont();
        if (classes == null || cont == null) {
            return Result.error("参数错误");
        }
        return mclassesService.edit(classes, cont) ? Result.success("修改成功") : Result.error("修改失败");
    }
    /**更新个人资料*/
    @PutMapping("/edit")
    public Result edit(@RequestBody Admin admin) {
        String aid = admin.getAid();
        String aname = admin.getAname();
        String adesc = admin.getAdesc();
        if (aid == null || aname == null || adesc == null) {
            return Result.error("参数错误");
        }
        return adminservice.edit(aid, aname, adesc) ? Result.success("修改成功") : Result.error("修改失败");
    }
    /**上传图片*/
    @PostMapping("/uploadImg")
    public Result uploadImg(@RequestPart("file") MultipartFile file, @RequestParam("aid") String aid) throws IOException, UpException {
        if (file == null || file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        UpYun upYun = new UpYun("sm101", "smroot", "你的秘钥信息");
        String filename = file.getOriginalFilename() + UUID.randomUUID().toString() + ".jpg";
        System.out.println("图片名称：" + filename);
        boolean re = upYun.writeFile(filename, file.getBytes(), false);
        String imageUrl = "http://sm101.test.upcdn.net/" + filename; // 构建图片地址
        Integer a = adminservice.addImgPath(imageUrl, aid);
        // 返回图片地址
        return Result.success(imageUrl); // 返回上传成功的图片地址
    }

}


