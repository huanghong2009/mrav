package com.mobcolor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@SpringBootApplication(scanBasePackages={"com.mobcolor.framework","com.mobcolor.ms"})
@Configuration
public class Application extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            registry.addResourceHandler("/apidoc/**").addResourceLocations("classpath:/apidoc/");
            super.addResourceHandlers(registry);
        } catch (Exception e) {
        }
    }



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
