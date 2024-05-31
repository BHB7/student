package com.example.mapper;

import com.example.entity.Mclasses;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Mclasses)表数据库访问层
 *
 * @author 7z
 * @since 2024-05-27 08:23:00
 */
@Mapper
public interface MclassesMapper {

    @Select("select * from mclasses where aid=#{aid}")
    List<Mclasses> getClasses(String aid);

    /**添加班级*/
    @Insert("insert into mclasses(aid,classes,cont) values(#{aid},#{classes},#{cont})")
    boolean addClass(String aid, String classes, String cont);

    @Select("select classes, aid from mclasses where classes=#{classes} and aid=#{aid}")
    List<String> sole(String classes, String aid);

    @Select("select sclass from student")
    List<String> count();

    @Delete("delete from mclasses where classes=#{id}")
    boolean delete(String id);

    /**更新资料*/
    @Update("update mclasses set cont=#{cont} where classes=#{classes}")
    boolean edit(String classes, String cont);
}
