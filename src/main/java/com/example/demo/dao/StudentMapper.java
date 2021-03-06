package com.example.demo.dao;
import com.example.demo.pojo.Student;          //引入实体类
import com.example.demo.pojo.TbMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentMapper {
    @Select("SELECT * FROM student")
    List<Student> findAll();

    /*
     * 同时用多对多查询
     */
    @Select("SELECT * FROM student")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "others", column = "id", many = @Many(select = "com.example.demo.dao.OthersMapper.getOthersByStudent_id"))
    })
    List<Student> findAllbyothers();

    /*
     * 同时用一对多查询
     */
    @Select("SELECT * FROM student where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "others", column = "id", many = @Many(select = "com.example.demo.dao.OthersMapper.getOthersByStudent_id"))
    })
    List<Student> findonebyothers(Long id);


    @Insert("INSERT INTO student(id, name) VALUES(#{id}, #{name})")
    int insertParam(@Param("id") String id, @Param("name") String name);

    @Insert("INSERT INTO student(student_id,name,age,sex,birthday) VALUES(#{student_id}, #{name}, #{age}, #{sex}, #{birthday})")
    int insertParamall(Student s);

   /*Xml方式查询*/
    Student selectByPrimaryKey(Long id,String keyword);

    /*Xml方式查询*/
    Student selectByothers(Long id,String keyword);

    /*Xml方式查询*/
    Student findByuid(String id);

    /*Xml方式查询*/
    Student findByname(String name);


    @Select("<script>"
            +"select * from student where 1=1"
            +"<if test='status != null'>"
            +"and status = #{status}"
            +"</if>"
            +"</script>")
    List<Student> findlistbyzj_Select(@Param("status") Integer status);


    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    List<Student> pageList(int start, int pagesize, String keyword);


    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    List<Student> pageListbyothers(int start, int pagesize, String keyword);

    /**
     * 查询 分页查询 count
     * @author 吴啸
     * @date 2020/02/26
     **/
    int pageListCount(int start,int pagesize,String keyword);

    /*Xml方式查询*/
     Map<String,Object> selectbyname(int start, int pagesize, String keyword);

     
}
