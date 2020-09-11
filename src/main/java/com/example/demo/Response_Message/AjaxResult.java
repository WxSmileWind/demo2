package com.example.demo.Response_Message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
@ApiModel("统一API响应结果封装")
@Getter
public class AjaxResult<T> implements Serializable {

//    private int code;
//    private String msg;
//    private Object data;
//    private Integer total;

//    public AjaxResult(RespCode respCode) {
//        this.code = respCode.getCode();
//        this.msg = respCode.getMsg();
//    }
//
//    public AjaxResult(RespCode respCode, Object data) {
//        this(respCode);    //当前类的构造方法 this()
//        this.data = data;
//    }

    @ApiModelProperty(value = "成功失败的标志",required = true)
    private final int code;
    /**
     * 响应信息，用来说明响应情况
     */
    @ApiModelProperty(value = "成功失败的响应信息",required = true)
    private final String msg;
    /**
     * 响应的具体数据
     */
    @ApiModelProperty(value = "成功失败的响应数据",required = false)
    private T data;



    public AjaxResult(T data) {
        this(RespCode.SUCCESS, data);
    }

    public AjaxResult(RespCode respCode, T data) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }
    public AjaxResult(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    /**
     * 返回成功消息
     *
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResult<T>  success(T data) {
        return new AjaxResult<>(RespCode.SUCCESS, data);
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> AjaxResult<T>  success() {
        return new AjaxResult<>(RespCode.SUCCESS);
    }




}
