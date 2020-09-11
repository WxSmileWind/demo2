package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.Event;


import java.util.List;
import java.util.Map;

public interface EventMapper extends BaseMapper<Event> {

    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/07/03
     **/
    List<Event> pageList(int start, int pagesize, String keyword);

    /**
     * 查询 分页查询 count
     * @author 吴啸
     * @date 2020/07/03
     **/
    int pageListCount(int start,int pagesize,String keyword);

    /*Xml方式查询*/
    Map<String,Object> selectbycontent(int start, int pagesize, String keyword);




}