package com.example.demo.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.DemoApplication;
import com.example.demo.annotation.PassToken;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.dao.StudentMapper;
import com.example.demo.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author wuxiao
 * @date 2019-12-06 10:14
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    StudentMapper studentMapper;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        System.out.println("接收到请求");
        logger.info("======---------开始进入拦截器------------=======");
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                Student user = studentMapper.findByuid(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                else{
                    logger.info("======---------将userid放到requert.setAttribute属性中------------=======");
                    // 将userId写入到request请求中
                   // httpServletRequest.setAttribute("UserId", user.getId());
                    httpServletRequest.setAttribute("UserToken", token);
                    httpServletRequest.setAttribute("UserId", user.getId());
                }

                // 验证 token，验证
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    DecodedJWT jwt= jwtVerifier.verify(token);
                    //判断token已过期
                    if (jwt.getExpiresAt().before(new Date()))
                    {
                        System.out.println("=============进入token过期");
                        System.out.println("token已过期");
                        throw new RuntimeException("token已过期");
                    }


                } catch (JWTVerificationException e) {
                    throw new RuntimeException("Invalid token,请重新登陆验证！");

//                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        logger.info("======---------拦截器执行结束------------=======");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
