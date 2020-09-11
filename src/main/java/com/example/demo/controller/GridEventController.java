package com.example.demo.controller;


import com.example.demo.Response_Message.AjaxResult;
import com.example.demo.Response_Message.RespCode;
import com.example.demo.Response_Message.RespEntity;
import com.example.demo.dao.EventMapper;
import com.example.demo.pojo.Event;
import com.example.demo.service.EventService;
import com.example.demo.service.TbMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.Response_Message.AjaxResult.success;

@Controller                           //控制器
@Api(tags = "事件接口")
@RequestMapping("/gridevent")
@RestController                       //responsebody 控制器
//允许跨域
@CrossOrigin(origins = "*")
public class GridEventController {

    @Resource
    private EventService eventService;




    @PostMapping("/listEvent")
   // @RequestMapping("/listEvent")
    @ApiOperation(value = "获取事件列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentpage", value = "当前页码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pagesize", value = "显示条数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "查询关键字", required = true, paramType = "query")
    })
    //List返回
    public Map<String, Object> listEvent(
            @RequestParam(required = false, defaultValue = "0") int currentpage,
            @RequestParam(required = false, defaultValue = "10") int pagesize,
            @RequestParam(required = false) String keyword
    ) {
//        int start=(currentpage-1)*pagesize;
//
//        List<Event> pageList = eventMapper.pageList(start, pagesize,keyword);
//        //总条数total
//        int totalCount = eventMapper.pageListCount(start, pagesize,keyword);
//        // result
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("pageList", pageList);
//        result.put("totalCount", totalCount);
       // ;

        return eventService.pageList(currentpage, pagesize,keyword);
    }



    @GetMapping
    @RequestMapping("/SelectEventByID")
    @ApiOperation(value = "获取单个事件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ID", value = "事件ID", required = true, paramType = "query")
    })
    public RespEntity SelectEventByID(@RequestParam(required = false) String id){

        //Event e=eventService.SelectOneEvent(id);
        return new RespEntity(RespCode.SUCCESS, eventService.SelectOneEvent(id));
    }


//    @GetMapping("/SelectEventByIDTest")
////    @RequestMapping("/SelectEventByIDTest")
//    @ApiOperation(value = "获取单个事件测试接口")
//    @ApiImplicitParam(name = "ID", value = "事件ID", required = true, paramType = "query")
//
//    public RespEntity SelectEventByIDTest(String ID){
//
//        //Event e=eventService.SelectOneEvent(id);
//        return new RespEntity(RespCode.SUCCESS, eventService.EventTest(ID));
//    }

    @GetMapping("/SelectEventByIDTest")
//    @RequestMapping("/SelectEventByIDTest")
    @ApiOperation(value = "获取单个事件测试接口")
    @ApiImplicitParam(name = "ID", value = "事件ID", required = true, paramType = "query")

    public AjaxResult<Event> SelectEventByIDTest(String ID){

        //Event e=eventService.SelectOneEvent(id);

        return success(eventService.EventTest(ID));
    }

}
