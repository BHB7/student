package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * (Mclasses)表实体类
 *
 * @author makejava
 * @since 2024-05-27 08:23:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mclasses")
public class Mclasses {
    @TableId
    private Integer id;

    private String aid;

    private String classes;

    private String cont;

}
