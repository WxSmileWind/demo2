package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.pojo.Student;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author wuxiao
 * @date 2019-12-06 10:11
 */
@Service("TokenService")
public class TokenService {
    public static final long EXPIRATION_TIME = 60*1000L; // 60秒过期


    public String getToken(Student student) {
        Date date = new Date(System.currentTimeMillis()+EXPIRATION_TIME);
        System.out.println("过期时间："+EXPIRATION_TIME);
        String token="";
        token= JWT.create().withAudience(student.getId().toString())// 将 user id 保存到 token 里面
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(student.getPassword()));// 以 password 作为 token 的密钥

        return token;
    }
}
