package com.example.demo.config;
import com.example.demo.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Value("${menu.img.uploadPath}")
    private String uploadPath;

    @Value("${menu.img.uploadUrl}")
    private String uploadUrl;

    @Value("${menu.img.zipPath}")
    private String zipPath;

    @Value("${menu.img.zipUrl}")
    private String zipUrl;
    /*捕获404错误，给全局异常控制器*/
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
       //        System.out.println("-----------------------registration");
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return registration;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /*开放静态资源路径*/
        registry.addResourceHandler("/favicon.ico")//favicon.ico
                .addResourceLocations("classpath:/static/");

      /*  registry.addResourceHandler("/smallapple/**")
                // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
                .addResourceLocations("file:G:/itemsource/smallapple/"); //媒体资源
*/

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
               .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/uploadpic/**").
                addResourceLocations("file:"+uploadPath);
        /*registry.addResourceHandler("/image/**")
                .addResourceLocations("file:"+uploadPath);*/


    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("-------------------------------------进入拦截器设置");
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        System.out.println("-------------------------------------结束拦截器设置");
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean(name="multipartResolver")
    public MultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }



}
