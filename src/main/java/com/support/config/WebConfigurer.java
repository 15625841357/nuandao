package com.support.config;

/**
 * @ClassName WebConfigurer
 * @Author 吴俊淇
 * @Date 2020/3/23 17:11
 * @Version 1.0
 **/

import com.support.interceptor.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Autowired
    private InterceptorConfig loginInterceptor;

    //配置cors
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("Content-Type","X-Requested-With","accept,Origin","Access-Control-Request-Method","Access-Control-Request-Headers","token")
//                .allowedMethods("*")
//                .allowedOrigins("*")
//                .allowCredentials(true);
    }

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
//                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
//        WebMvcConfigurer.super.addResourceHandlers(registry);

    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //.excludePathPatterns("/", "/static/**")
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**").excludePathPatterns("/user/register_login", "/static/**");
    }
}
