package com.example.demo.service;

import com.example.demo.Response_Message.RespCode;
import com.example.demo.Response_Message.RespEntity;
import com.example.demo.dao.TbMessageMapper;
import com.example.demo.pojo.TbMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 留言信箱
 * @author 吴啸
 * @date 2020-02-26 14:48:04
 */
@Service
public class TbMessageServiceImpl implements TbMessageService {

    @Resource
    private TbMessageMapper tbMessageMapper;


    @Override
    public RespEntity insert(TbMessage tbMessage) {

        // valid
        if (tbMessage == null) {
            return new RespEntity(RespCode.FAILCODE, "必要参数缺失");
        }

        tbMessageMapper.insert(tbMessage);
        return  new RespEntity(RespCode.SUCCESS);
    }


    @Override
    public RespEntity delete(int id) {
        int ret = tbMessageMapper.delete(id);
        return ret>0?new RespEntity(RespCode.SUCCESS):new RespEntity(RespCode.FAILCODE);
    }


    @Override
    public RespEntity update(TbMessage tbMessage) {
        int ret = tbMessageMapper.update(tbMessage);
        return ret>0?new RespEntity(RespCode.SUCCESS):new RespEntity(RespCode.FAILCODE);
    }


    @Override
    public TbMessage load(int id) {
        return tbMessageMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int currentpage, int pagesize,String username) {
        int start=(currentpage-1)*pagesize;
        System.out.println("===============start:"+start);
        //列表list
        List<TbMessage> pageList = tbMessageMapper.pageList(start, pagesize,username);
        //总条数total
        int totalCount = tbMessageMapper.pageListCount(start, pagesize,username);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

}

