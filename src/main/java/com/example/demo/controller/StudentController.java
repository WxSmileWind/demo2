package com.example.demo.controller;
import com.example.demo.DemoApplication;
import com.example.demo.Response_Message.RespCode;
import com.example.demo.Response_Message.RespEntity;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.config.ymConfig;
import com.example.demo.dao.StudentMapper;   //引用mapper
import com.example.demo.dao.TbMessageMapper;
import com.example.demo.lib.ErrorCode;
import com.example.demo.lib.NetSDKLib;
import com.example.demo.lib.hc;
import com.example.demo.pojo.Student;                            //引用实体类
import com.example.demo.pojo.TbMessage;
import com.example.demo.service.TokenService;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import io.swagger.annotations.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;   //引入自动装载 @Autowired
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;                //引入控制器Controller
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller                           //控制器
@Api(tags = "学生测试接口")
@RequestMapping("/student")
@RestController                       //responsebody 控制器
public class StudentController {



    @Value("${menu.img.uploadPath}")
    private String uploadPath;

    @Value("${menu.img.uploadUrl}")
    private String uploadUrl;

    @Value("${menu.img.zipPath}")
    private String zipPath;

    @Value("${menu.img.zipUrl}")
    private String zipUrl;


    private StudentMapper studentMapper;
    @Resource
    private TbMessageMapper tbMessageMapper;
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
    @Cacheable(value = "emp" ,key = "targetClass + methodName +#id",condition="#id==1")
    @ApiOperation(value = "根据id获取学生个人信息")
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
    @ApiOperation(value = "插入学生")
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
    @ApiOperation(value = "登陆")
    @PostMapping("/login")
    public RespEntity login(@RequestBody Student student){

       // Student userForBase=studentMapper.findByname(student.getName());
        System.out.println("password:"+student.getPassword());
       //System.out.println("userForBase password:"+userForBase.getPassword());
        return  new RespEntity(RespCode.SUCCESS,student.getPassword());
    }


    @UserLoginToken
    @GetMapping("/getMessage")
    public RespEntity getMessage(HttpServletRequest request){
        logger.info("======---------userid:"+request.getAttribute("UserId")+"------------=======");
        return  new RespEntity(RespCode.PassToken);
    }


    /**
     * 查询 分页查询
     * @author 吴啸
     * @date 2020/02/26
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int currentpage,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false, defaultValue = "10") int pagesize) {
        int start=(currentpage-1)*pagesize;
        System.out.println("start:"+start);
        System.out.println("pagesize:"+pagesize);
        System.out.println("keyword:"+keyword);
        //列表list
        List<Student> pageList = studentMapper.pageListbyothers(start, pagesize,keyword);
        //总条数total
        int totalCount = studentMapper.pageListCount(start, pagesize,keyword);
        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
       // System.out.println("currentpage:"+currentpage);
       // System.out.println("pagesize:"+pagesize);
        //return tbMessageService.pageList(currentpage, pagesize,username);
    }

    @ApiOperation(value = "上传图片接口")
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public RespEntity uploadImage(@ApiParam(value = "图片", required = true) @RequestParam("file") MultipartFile file) {
        logger.info("进入上传接口");
        if (file.isEmpty()) {
            logger.info("上传文件不能为空！");
        }
        return uploadImg(file);
    }

    /**
     * 上传图片
     * @Description:
     * @param: MultipartRequest file, HttpServletRequest request,  IdForm form
     * @return：RestResult<Object>
     * @author: zhanghd
     * @date: 2020年3月4日 下午2:39:24
     */
    public RespEntity uploadImg(@RequestParam("file") MultipartFile  file) {
        String oldName = file.getOriginalFilename();
        String imgType = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        String name = UUID.randomUUID().toString()+imgType; // 图片名
        String realpath = uploadPath;
        String fileName = writeUploadFile(file, realpath, name);
        String url = uploadUrl + fileName;
        return  new RespEntity(RespCode.SUCCESS,url);
    }

    /**
     * 文件上传处理
     * @Description: 上传图片
     * @param:MultipartFile file, String realpath, String fileName
     * @return：String fileName
     * @author: zhanghd
     * @date: 2018年12月21日 下午5:00:56
     */
    public static String writeUploadFile(MultipartFile file, String realpath, String fileName) {
        File fileDir = new File(realpath);
        if (!fileDir.exists())
            fileDir.mkdirs();

        InputStream input = null;
        FileOutputStream fos = null;
        try {
            input = file.getInputStream();
            fos = new FileOutputStream(realpath + "/" + fileName);
            IOUtils.copy(input, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fos);
        }
        return fileName;
    }



}
