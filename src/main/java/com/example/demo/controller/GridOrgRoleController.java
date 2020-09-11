package com.example.demo.controller;


import com.example.demo.Response_Message.AjaxResult;
import com.example.demo.pojo.Event;
import com.example.demo.pojo.GridOrgRole;
import com.example.demo.service.EventService;
import com.example.demo.service.GridOrgRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.example.demo.Response_Message.AjaxResult.success;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴啸
 * @since 2020-09-09
 */
@RestController
@Api(tags = "组织角色表")
@RequestMapping("/grid-org-role")
public class GridOrgRoleController {
    @Resource
    private GridOrgRoleService gridOrgRoleService;


    @GetMapping("/SelectRole")
//    @RequestMapping("/SelectEventByIDTest")
    @ApiOperation(value = "查询组织角色")
    @ApiImplicitParam(name = "ID", value = "ID", required = true, paramType = "query")

    public AjaxResult<GridOrgRole> SelectRole(String ID){

        //Event e=eventService.SelectOneEvent(id);

        return success(gridOrgRoleService.getById(ID));
    }
}

