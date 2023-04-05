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
    public void preDestroy() throws InterruptedException {
        // 关闭服务器前的收尾工作......
        // 1. 关闭Redis消费者线程
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int activeThreadNum = currentGroup.activeCount();
        Thread[] activeThreads = new Thread[activeThreadNum];
        currentGroup.enumerate(activeThreads);
        Thread consumerThread = null;
        for (int i = 0; i < activeThreadNum; i++){
            if("consumer".equals(activeThreads[i].getName())){
                consumerThread = activeThreads[i];
                break;
            }
        }
        if(consumerThread != null){
            consumerThread.interrupt();
            consumerThread.join();
        }

        logger.info("SpringBoot process is destroyed!");
    }

}
