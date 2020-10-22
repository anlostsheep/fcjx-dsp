package com.richstonedt.fcjx.advertisement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dengzhen
 * @since fcjx-dsp 1.0.0
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.richstonedt.fcjx.advertisement")
public class AdvertisementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertisementApplication.class, args);
    }

}
