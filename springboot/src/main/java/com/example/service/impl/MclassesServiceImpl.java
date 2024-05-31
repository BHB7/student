package com.example.service.impl;

import com.example.entity.Mclasses;
import com.example.mapper.MclassesMapper;
import com.example.service.MclassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Mclasses)表服务实现类
 *
 * @author makejava
 * @since 2024-05-27 08:23:03
 */
@Service("mclassesService")
public class MclassesServiceImpl implements MclassesService{

    @Autowired
    private MclassesMapper mclassesMapper;
    @Override
    public List<Mclasses> getClasses(String aid) {
        if (aid != null){
            return mclassesMapper.getClasses(aid);
        }
        return null;
    }

    @Override
    public boolean addClass(String aid, String classes, String cont) {
        if (aid != null && classes != null && cont != null){
            return mclassesMapper.addClass(aid,classes,cont) ? true : false;
        }
        return false;
    }

    // 唯一性
    @Override
    public boolean sole(String classes, String aid) {
        if (classes != null){
            if (mclassesMapper.sole(classes, aid).size() == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public Map count() {
       List<String> list = mclassesMapper.count();
       Map<String, Integer> map = new HashMap<>();
       for (String s : list) {
           if (map.containsKey(s)){
               map.put(s,map.get(s)+1);
           }else {
               map.put(s,1);
           }
       }
        return map;
    }

    // 删除班级
    @Override
    public boolean delete(String classes) {
        if (classes != null){
            return mclassesMapper.delete(classes);
        }
        return false;
    }

    @Override
    public boolean edit(String classes, String cont) {
        if (classes != null && cont != null){
            return mclassesMapper.edit(classes,cont);
        }
        return false;
    }
}
