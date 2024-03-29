package com.mashibing.apidriver.interceptor;

import org.springframework.context.annotation.Bean;
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

    /**
     * 在拦截器初始化时候就把拦截器里面所有需要的bean都实例化
     * 防止后面需要用时没有实例化bean
     *
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                //拦截路径
                .addPathPatterns("/**")
                //不拦截路径
                .excludePathPatterns("/noauthTest")
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/verification-code-check")
                .excludePathPatterns("/point/upload")
                .excludePathPatterns("/token-refresh");
    }
}
