package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * (Student)表数据库访问层
 *
 * @author 7z
 * @since 2024-05-24 09:27:09
 */
@Mapper
public interface StudentMapper {

    @Select("select * from admin where sid=#{aid} and spwd=#{apwd}")
    Student login(Student student);

    @Select("select * from student")
    List<Student> studentList();

    @Delete("delete from student where id=#{id}")
    boolean delete(Integer id);

    @Insert("insert into student(sid,sname,ssex,sclass,spwd) values(#{sid},#{sname},#{ssex},#{sclass},#{spwd})")
    boolean add(Student student);

    @Update("update student set sid=#{sid},sname=#{sname},ssex=#{ssex},sclass=#{sclass} where id=#{id}")
    boolean update(Student student);

    @Select("select * from student")
    List<Student> studentListPage();

    /**模糊查询*/
    @SelectProvider(type = SqlBuilder.class, method = "buildQuery")
    List<Student> findByParams(@Param("params") Map<String, Object> params);
    class SqlBuilder {
        public String buildQuery(Map<String, Object> params) {
            return new SQL() {{
                SELECT("*");
                FROM("student");
                if (params.get("params") != null) {
                    Map<String, Object> searchParams = (Map<String, Object>) params.get("params");
                    if (searchParams.get("param1") != null) {
                        WHERE("sid = #{params.param1}");
                    }
                    if (searchParams.get("param2") != null) {
                        WHERE("sname LIKE CONCAT('%', #{params.param2}, '%')");
                    }
                    if (searchParams.get("param3") != null) {
                        WHERE("sclass LIKE CONCAT('%', #{params.param3}, '%')");
                    }
                    if (searchParams.get("param4") != null) {
                        WHERE("ssex = #{params.param4}");
                    }
                }
            }}.toString();
        }
    }
}

