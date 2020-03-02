package com.example.demo.service;

import com.example.demo.Response_Message.RespEntity;
import com.example.demo.pojo.TbMessage;

import java.util.Map;

/**
 * @description 留言信箱
 * @author 吴啸
 * @date 2020-02-26 14:48:04
 */
public interface TbMessageService {

    /**
     * 新增
     */
    public RespEntity insert(TbMessage tbMessage);

    /**
     * 删除
     */
    public RespEntity delete(int id);

    /**
     * 更新
     */
    public RespEntity update(TbMessage tbMessage);

    /**
     * 根据主键 id 查询
     */
    public TbMessage load(int id);

    /**
     * 分页查询
     */
    public Map<String,Object> pageList(int start, int pagesize,String username);

}
