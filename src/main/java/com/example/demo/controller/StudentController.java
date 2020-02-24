package com.example.demo.controller;
import com.example.demo.DemoApplication;
import com.example.demo.Response_Message.RespCode;
import com.example.demo.Response_Message.RespEntity;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.dao.StudentMapper;   //引用mapper
import com.example.demo.pojo.Student;                            //引用实体类
import com.example.demo.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;   //引入自动装载 @Autowired
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;                //引入控制器Controller
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;                //引用List集合
import java.util.Map;
import java.util.TreeMap;

@Controller                           //控制器
@Api(tags = "测试接口")
@RequestMapping("/student")
@RestController                       //responsebody 控制器
public class StudentController {


    private StudentMapper studentMapper;
    private TokenService tokenService;
    protected static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    public StudentController(StudentMapper studentMapper, TokenService tokenService) {
        this.studentMapper = studentMapper;
        this.tokenService = tokenService;
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/listStudent")
    //List返回
    public List<Student> listStudent(String data) {
        List<Student> students = studentMapper.findAll();
        //students[0].
        Map<String,List<Student>> map = new HashMap<>();

        //  model.addAttribute("students", students);
        return students;
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/listmapStudent")
    //map<key,value>返回
    public RespEntity listmapStudent(String data) {
        List<Student> students = studentMapper.findAll();
        Map<String,List<Student>> map = new TreeMap<>();
        return new RespEntity(RespCode.SUCCESS, students);
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/listselStudent")
    //map<key,value>返回
    public RespEntity listselStudent() {
        // List<Student> students = studentMapper.Sel();
        List<Student> students= studentMapper.findAllbyothers();
        // Map<String,List<Student>> map = new TreeMap<>();
        return new RespEntity(RespCode.SUCCESS,students);
    }
    //设置方法结果缓存
    @Cacheable(value = "emp" ,key = "targetClass + methodName +#p0")
    @ApiOperation(value = "测试 - GET请求参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true, paramType = "query")
    })
    @RequestMapping(value = "/listoneStudent",method = {RequestMethod.GET,RequestMethod.POST})
    //map<key,value>返回
    public RespEntity listoneStudent(Long id) {
        // List<Student> students = studentMapper.Sel();
        List<Student> students= studentMapper.findonebyothers(id);
        logger.info("数据库查询结果："+(new RespEntity(RespCode.SUCCESS,students)).toString());
//        Map<String,List<Student>> map = new TreeMap<>();
        return new RespEntity(RespCode.SUCCESS,students);
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/listbyzj_student")
    //通过@Select注解实现sql查询, 请使用Integer代替int
    public RespEntity listbyzj_student(Integer status) {
        // List<Student> students = studentMapper.Sel();
        if(status==null){
        }
        System.out.println("status:"+status);
        List<Student> students= studentMapper.findlistbyzj_Select(status);
        // Map<String,List<Student>> map = new TreeMap<>();
        return new RespEntity(RespCode.SUCCESS,students);
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/selectone")
    //map<key,value>返回
    public RespEntity oneStudent(@RequestParam Long id,@RequestParam String keyword) throws Exception {
        // List<Student> students = studentMapper.Sel();
        Student students= studentMapper.selectByPrimaryKey(id,keyword);
        // Map<String,List<Student>> map = new TreeMap<>();
        return new RespEntity(RespCode.SUCCESS,students);
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/selectonebyothers")
    //map<key,value>返回
    public RespEntity oneothersStudent(@RequestParam Long id,@RequestParam String keyword) throws Exception {
        // List<Student> students = studentMapper.Sel();
        Student students= studentMapper.selectByothers(id,keyword);
        // Map<String,List<Student>> map = new TreeMap<>();
        return new RespEntity(RespCode.SUCCESS,students);
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/ResponseBody")
    //map<key,value>返回
    public RespEntity response_body(@RequestBody Student student) throws Exception {

        Map<String,String> students=new HashMap<>();
        if (null != student) {
            students.put("获取到name",student.getName());
            students.put("获取到age",String.valueOf(student.getAge()));
            return  new RespEntity(RespCode.SUCCESS,student);
        }
        else {
            return  new RespEntity(RespCode.WARN1);
        }
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/insert_student")
    //map<key,value>返回
    public RespEntity insert_student(@RequestBody Student student) throws Exception {
            int count= studentMapper.insertParamall(student);
            if(count>0){
                return  new RespEntity(RespCode.SUCCESS);
            }
            else {
                return  new RespEntity(RespCode.WARN1);
            }
    }

    @PostMapping
    @GetMapping
    @RequestMapping("/insertStudent")
    public String insertStudent(String id,String name) {
        int count=studentMapper.insertParam(id,name);
        //  model.addAttribute("students", students);
        return "提交成功，返回影响行数："+count;
    }


    //登录
    @PostMapping("/login")
    public RespEntity login(@RequestBody Student student){

        Student userForBase=studentMapper.findByname(student.getName());
        if(userForBase==null){
            return  new RespEntity(RespCode.NameError);
            //jsonObject.put("message","登录失败,用户不存在");
           // return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(student.getPassword())){
                return  new RespEntity(RespCode.PassError);
            }else {
                String token = tokenService.getToken(userForBase);
                Map<String,Object> map = new TreeMap<>();
                map.put("token",token);
                map.put("user",userForBase);
                return  new RespEntity(RespCode.SUCCESS,map);
//                jsonObject.put("token", token);
//                jsonObject.put("user", userForBase);
//                return jsonObject;
            }
        }
    }


    @UserLoginToken
    @GetMapping("/getMessage")
    public RespEntity getMessage(){
        return  new RespEntity(RespCode.PassToken);
    }

}
