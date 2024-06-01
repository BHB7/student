package com.example.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Admin)表实体类
 *
 * @author 7z
 * @since 2024-05-24 11:55:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class Admin  {
    @TableId
    private Integer id;

    private String aid;
    
    private String aname;
    
    private String apwd;
    // 描述
    private String adesc;
    // 头像
    private String aavatar;

}
