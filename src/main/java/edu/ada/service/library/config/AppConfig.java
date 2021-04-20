package edu.ada.service.library.config;

import edu.ada.service.library.interceptor.AuthInterceptor;
import edu.ada.service.library.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    private UserRepository userRepository;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(userRepository)).addPathPatterns("/**").excludePathPatterns("/auth/**").excludePathPatterns("/library/public/**");
    }
}