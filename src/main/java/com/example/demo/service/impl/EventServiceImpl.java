package com.example.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.EventMapper;
import com.example.demo.dao.TbMessageMapper;
import com.example.demo.pojo.Event;
import com.example.demo.pojo.PageInfo;
import com.example.demo.pojo.TbMessage;
import com.example.demo.service.EventService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 留言信箱
 * @author 吴啸
 * @date 2020-07-06 12:00:00
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    @Resource
    private EventMapper eventMapper;
    //注入JdbcTemplate模板对象
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String,Object> pageList(int currentpage, int pagesize, String keyword) {
        int start=(currentpage-1)*pagesize;

        List<Event> pageList = eventMapper.pageList(start, pagesize,keyword);
        //总条数total
        int totalCount = eventMapper.pageListCount(start, pagesize,keyword);
        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);


        //eventMapper.selectPage()



        return result;
    }

    @Override
    public IPage<Event> queryEventList(Page<Event> page, QueryWrapper queryWrapper){
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Event SelectOneEvent(Serializable id){
        //baseMapper.

        //return baseMapper.selectOne();
        return baseMapper.selectById(id);
    }

    @Override
    @DS("slave_1")
    public Event EventTest(Serializable id){
        //定义SQL语句
        String sql = "select * from base_area where AreaId = ?";
        RowMapper<Event> rowMapper = new BeanPropertyRowMapper<>(Event.class);
        //执行查询方法

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

}

