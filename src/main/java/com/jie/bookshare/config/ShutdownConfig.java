package com.jie.bookshare.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class ShutdownConfig {

    /**
     * HTTP访问：http://{ip}:{port}/actuator/shutdown，即可完成优雅关闭项目
     */
    @Bean
    public TerminateBean getTerminateBean(){
        return new TerminateBean();
    }
}

class TerminateBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PreDestroy
    public void preDestroy() {
        // 关闭服务器前的收尾工作......

        logger.info("SpringBoot process is destroyed!");
    }

}
