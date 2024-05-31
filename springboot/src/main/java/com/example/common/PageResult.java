package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    private Integer total;
    private String code;
    private String msg;
    private Object data;


    public static PageResult success() {
        PageResult pageResult = new PageResult();
        pageResult.setCode("200");
        pageResult.setMsg("请求成功");
        pageResult.setTotal(0);
        return pageResult;
    }

    public static PageResult success(Integer total ,Object data) {
        PageResult pageResult = success();
        pageResult.setData(data);
        pageResult.setTotal(total);
        return pageResult;
    }

}
