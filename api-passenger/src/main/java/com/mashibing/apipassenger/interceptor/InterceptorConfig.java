package com.mashibing.apipassenger.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Gloria
 * @Description: 把拦截器注入到spring容器中，决定拦截以及不拦截的路径
 * @Date: Created in 4:45 PM 10/20/22
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                //拦截路径
                .addPathPatterns("/**")
                //不拦截路径
                .excludePathPatterns("/noauthTest");
    }
}
