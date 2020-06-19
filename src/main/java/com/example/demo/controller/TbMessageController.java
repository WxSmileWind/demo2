package com.example.demo.controller;

import com.example.demo.Response_Message.RespEntity;
import com.example.demo.pojo.TbMessage;
import com.example.demo.service.TbMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description 留言信箱
 * @author 吴啸
 * @date 2020-02-26 14:48:04
 */
                           //控制器
@Api(tags = "留言信息接口")
@RestController
@RequestMapping(value = "/TbMessage")
public class TbMessageController {

    @Resource
    private TbMessageService tbMessageService;

    /**
     * 新增
     * @author 吴啸
     * @date 2020/02/26
     **/

    @PostMapping("/insert")
    @ApiOperation(value = "提交留言信息")


    public RespEntity insert(@RequestBody TbMessage tbMessage,String type){
        System.out.println("type:"+type);
        return tbMessageService.insert(tbMessage);
    }

    /**
     * 刪除
     * @author 吴啸
     * @date 2020/02/26
     **/
    @RequestMapping("/delete")
    public RespEntity delete(int id){
        return tbMessageService.delete(id);
    }

    /**
     * 更新
     * @author 吴啸
     * @date 2020/02/26
     **/
    @RequestMapping("/update")
    public RespEntity update(TbMessage tbMessage){
        return tbMessageService.update(tbMessage);
    }

    /**
     * 查询 根据主键 id 查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    @RequestMapping("/load")
    public TbMessage load(int id){
        return tbMessageService.load(id);
    }

    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int currentpage,
                                        @RequestParam(required = false) String username,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {
        System.out.println("currentpage:"+currentpage);
        System.out.println("pagesize:"+pagesize);

        //tbMessageService.pa
        return tbMessageService.pageList(currentpage, pagesize,username);
    }

}

