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


    // 设备信息
    public static NetSDKLib.NET_DEVICEINFO_Ex m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();
    public static NetSDKLib netsdk 		= NetSDKLib.NETSDK_INSTANCE;
    public static NetSDKLib configsdk 	= NetSDKLib.CONFIG_INSTANCE;
    private static boolean bInit    = false;
    private static boolean bLogopen = false;

    // 设备断线通知回调
    private static DisConnect disConnect       = new DisConnect();

    // 设备断线通知回调
    //private static DisConnect disConnect       = new DisConnect();

    // 网络连接恢复
    private static HaveReConnect haveReConnect = new HaveReConnect();

    // 登陆句柄
    public static NetSDKLib.LLong m_hLoginHandle = new NetSDKLib.LLong(0);

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


    @PostMapping("/dahuakz")
    public RespEntity dahuakz(){
        bInit = netsdk.CLIENT_Init(null,null);
        String result="";
        if(!bInit) {
            System.out.println("Initialize SDK failed");
            result="Initialize SDK failed";
            return new RespEntity(RespCode.FAIL,result);
        }

        // 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
        // 此操作为可选操作，但建议用户进行设置
        netsdk.CLIENT_SetAutoReconnect(haveReConnect, null);

        //设置登录超时时间和尝试次数，可选
        int waitTime = 5000; //登录请求响应超时时间设置为5S
        int tryTimes = 1;    //登录时尝试建立链接1次
        netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);

        NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
        netParam.nConnectTime = 10000;      // 登录时尝试建立链接的超时时间
        netParam.nGetConnInfoTime = 3000;   // 设置子连接的超时时间
        netsdk.CLIENT_SetNetworkParam(netParam);

        result="设备初始化成功！";

        boolean logins= login_dh("192.168.0.150",37777,"admin","admin123");
        if(logins==true){
            result="设备登录成功";

            boolean kz= New_OpenStrobe();
            if(kz==true){
                result="设备开闸成功";
            }
            else{
                result="设备开闸失败";
            }
            logout();
            return new RespEntity(RespCode.SUCCESS,result);
        }
        else{
            result="设备登录失败";
            return new RespEntity(RespCode.FAIL,result);
        }

        //return new RespEntity(RespCode.SUCCESS,result);


    }


    @PostMapping("/dahuagz")
    public  RespEntity dahuagz(){
        bInit = netsdk.CLIENT_Init(null,null);
        String result="";
        if(!bInit) {
            System.out.println("Initialize SDK failed");
            result="Initialize SDK failed";
            return new RespEntity(RespCode.FAIL,result);
        }
        // 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
        // 此操作为可选操作，但建议用户进行设置
        netsdk.CLIENT_SetAutoReconnect(haveReConnect, null);

        //设置登录超时时间和尝试次数，可选
        int waitTime = 5000; //登录请求响应超时时间设置为5S
        int tryTimes = 1;    //登录时尝试建立链接1次
        netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);

        NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
        netParam.nConnectTime = 10000;      // 登录时尝试建立链接的超时时间
        netParam.nGetConnInfoTime = 3000;   // 设置子连接的超时时间
        netsdk.CLIENT_SetNetworkParam(netParam);

        result="设备初始化成功！";

        boolean logins= login_dh("192.168.0.150",37777,"admin","admin123");
        if(logins==true){
            result="设备登录成功";

            boolean kz= New_CloseStrobe();
            if(kz==true){
                result="设备关闸成功";
            }
            else{
                result="设备关闸失败";
            }
            logout();
            return new RespEntity(RespCode.SUCCESS,result);
        }
        else{
            result="设备登录失败";
            return new RespEntity(RespCode.FAIL,result);
        }
    }


    @PostMapping("/dahuawhite")
    public  RespEntity dahuawhite(@ApiParam(value = "csv文件", required = true) @RequestParam("file") MultipartFile file){
        logger.info("进入上传接口");
        if (file.isEmpty()) {
            logger.info("上传文件不能为空！");
        }
       // netsdk.CLIENT_FileTransmit(m_hLoginHandle,0x0003, )
        return  new RespEntity(RespCode.SUCCESS,"执行成功！");



    }

    /////////////////面板///////////////////
    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
    private static class DisConnect implements NetSDKLib.fDisConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            logger.info("ReConnect Device["+pchDVRIP+"] Port["+nDVRPort+"]",pchDVRIP, nDVRPort);
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);

        }
    }

    // 网络连接恢复，设备重连成功回调
    // 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            logger.info("ReConnect Device["+pchDVRIP+"] Port["+nDVRPort+"]",pchDVRIP, nDVRPort);
            System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);

        }
    }


    /**
     * \if ENGLISH_LANG
     * Login Device
     * \else
     * 登录设备
     * \endif
     */
    public static boolean login_dh(String m_strIp, int m_nPort, String m_strUser, String m_strPassword) {
        IntByReference nError = new IntByReference(0);
        m_hLoginHandle = netsdk.CLIENT_LoginEx2(m_strIp, m_nPort, m_strUser, m_strPassword, 0, null, m_stDeviceInfo, nError);
        if(m_hLoginHandle.longValue() == 0) {
            System.err.printf("Login Device[%s] Port[%d]Failed. %s\n", m_strIp, m_nPort, "\n{error code: (0x80000000|" + (netsdk.CLIENT_GetLastError() & 0x7fffffff) +").参考  NetSDKLib.java }"
                    + " - {error info:" + ErrorCode.getErrorCode(netsdk.CLIENT_GetLastError()) + "}\n");
        } else {
            System.out.println("Login Success [ " + m_strIp + " ]");
        }

        return m_hLoginHandle.longValue() == 0? false:true;
    }


    /**
     * 新版本开闸
     */
    public static boolean New_OpenStrobe() {
        NetSDKLib.NET_CTRL_OPEN_STROBE openStrobe = new NetSDKLib.NET_CTRL_OPEN_STROBE();
        openStrobe.nChannelId = 0;
        //String plate = new String("浙A888888");
        String plate=new String("");
        System.out.println("plate:"+plate);

        System.arraycopy(plate.getBytes(), 0, openStrobe.szPlateNumber, 0, plate.getBytes().length);
        openStrobe.write();
        if (netsdk.CLIENT_ControlDeviceEx(m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_OPEN_STROBE, openStrobe.getPointer(), null, 3000)) {
            System.out.println("Open Success!");
        } else {
            System.err.println("Failed to Open.");
            return false;
        }
        openStrobe.read();

        return true;
    }

    /**
     * 新版本关闸
     */
    public static boolean New_CloseStrobe() {
        NetSDKLib.NET_CTRL_CLOSE_STROBE closeStrobe = new NetSDKLib.NET_CTRL_CLOSE_STROBE();
        closeStrobe.nChannelId = 0;
        closeStrobe.write();
        if (netsdk.CLIENT_ControlDeviceEx(m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CLOSE_STROBE, closeStrobe.getPointer(), null, 3000)) {
            System.out.println("Close Success!");
        } else {
            System.err.println("Failed to Close.");
            return false;
        }
        closeStrobe.read();

        return true;
    }


    /**
     * \if ENGLISH_LANG
     * Logout Device
     * \else
     * 登出设备
     * \endif
     */
    public static boolean logout() {
        if(m_hLoginHandle.longValue() == 0) {
            return false;
        }

        boolean bRet = netsdk.CLIENT_Logout(m_hLoginHandle);
        if(bRet) {
            m_hLoginHandle.setValue(0);
        }

        return bRet;
    }


}
