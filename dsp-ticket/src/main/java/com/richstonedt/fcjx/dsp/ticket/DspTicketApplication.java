package com.richstonedt.fcjx.dsp.ticket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.richstonedt.fcjx.dsp.ticket.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class DspTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(DspTicketApplication.class, args);
    }

}
