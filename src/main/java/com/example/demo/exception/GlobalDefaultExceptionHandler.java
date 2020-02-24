package com.example.demo.exception;
import com.example.demo.DemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    protected static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    //声明要捕获的异常
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public <T> Result<?> defaultExcepitonHandler(Exception e) {
        System.out.println("异常："+e);
        e.printStackTrace();
        if(e instanceof BusinessException) {
           // Log.error(this.getClass(),"业务异常："+e.getMessage());
            BusinessException businessException = (BusinessException)e;
            return ResultUtil.error(businessException.getCode(), businessException.getMessage());
        }

        logger.error(e.toString());
        //未知错误
        return ResultUtil.error(-1, "系统异常：\\n"+e);
    }




}
