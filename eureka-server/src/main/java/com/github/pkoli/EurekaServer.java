package com.github.pkoli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {
    private static final String CLUSTER_NAME = "TEST-HAZELCAST";

    static {
        System.setProperty("spring.application.name", CLUSTER_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }
}
