package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Event;
import com.example.demo.pojo.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EventService  extends IService<Event> {
    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/07/03
     **/
    public Map<String,Object>  pageList(int start, int pagesize, String keyword);

    /**
     * 查询条件+事件分页查询
     * @author 吴啸
     * @date 2020/07/03
     **/
    public IPage<Event> queryEventList(Page<Event> page, QueryWrapper queryWrapper);


    /*    根据事件id
    获取事件*/
    public Event SelectOneEvent(Serializable id);

    public Event EventTest(Serializable id);
}
