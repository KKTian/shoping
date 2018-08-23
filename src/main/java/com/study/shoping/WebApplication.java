package com.study.shoping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * 描述:
 *
 * @outhor kk
 * @create 2018-05-30 17:58
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.study.shoping"})
@ServletComponentScan
@EnableScheduling
public class WebApplication {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate(){
        return restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }

}