package com.example.service;

import com.example.entity.Mclasses;

import java.util.List;
import java.util.Map;

/**
 * (Mclasses)表服务接口
 *
 * @author makejava
 * @since 2024-05-27 08:23:03
 */
public interface MclassesService {

    List<Mclasses> getClasses(String aid);

    boolean addClass(String aid, String classes, String desc);

    boolean sole(String classes, String aid);

    Map count();

    boolean delete(String classes);

    boolean edit(String classes, String cont);
}
