package com.linyi.demostarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(XxxxProperties.class)
@Configuration
public class DemoAutoConfiguration {

    @Autowired
    private XxxxProperties properties;

    @Bean
    public DemoService demoService() {
    	DemoService helloService = new DemoService();
        return helloService;
    }

}
