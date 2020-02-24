package com.example.demo.dao;
import com.example.demo.pojo.Others;          //引入实体类
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OthersMapper {
    /*
     * 根据用户id查询用户相关其他信息
     */
    @Select("SELECT * FROM `others` WHERE student_id = #{student_id}")
    @Results({
            @Result(property = "name",column = "name"),
            @Result(property = "desc",column = "desc")
    })
    List<Others> getOthersByStudent_id(Integer student_id);
}
