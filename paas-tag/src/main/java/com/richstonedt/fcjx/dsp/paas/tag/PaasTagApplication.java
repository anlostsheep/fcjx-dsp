package com.richstonedt.fcjx.dsp.paas.tag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.richstonedt.fcjx.dsp.paas.tag.dao")
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class PaasTagApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaasTagApplication.class, args);
    }

}
