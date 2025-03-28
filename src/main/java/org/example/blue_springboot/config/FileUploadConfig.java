/*package org.example.blue_springboot.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置单个文件最大10MB
        factory.setMaxFileSize("10MB");
        // 设置整个请求体最大10MB
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}*/
