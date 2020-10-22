package com.richstonedt.fcjx.dsp.blackwhitelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BlackWhiteListApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackWhiteListApplication.class, args);
    }

}
