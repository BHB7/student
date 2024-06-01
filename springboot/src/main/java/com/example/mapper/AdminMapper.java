package com.example.mapper;


import com.example.entity.Admin;
import com.example.entity.Mclasses;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (Admin)表数据库访问层
 *
 * @author 7z
 * @since 2024-05-24 11:55:34
 */
@Mapper
public interface AdminMapper {

    @Insert("insert into admin(aid,aname,apwd) values(#{aid},#{aname},#{apwd})")
     boolean register(String aid, String aname, String apwd);

    @Select("select * from admin where aid=#{aid} and apwd=#{apwd}")
    Admin login(String aid, String apwd);

    @Select("select * from admin where aid=#{aid}")
    List<Admin> sole(String aid);

    @Update("update admin set aavatar=#{imgPath} where aid=#{userId}")
    Integer addImgPath(String imgPath, String userId);

    @Update("update admin set aname=#{aname},adesc=#{adesc} where aid=#{aid}")
    boolean edit(String aid, String aname, String adesc);
}
