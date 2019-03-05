package com.github.pkoli;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.hazelcast.web.WebFilter;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    @Value("${hazelcast.port:5701}")
    private int hazelcastPort;

    @Bean
    public Config hazelcastConfig(EurekaClient eurekaClient) {
        Config config = new Config();
        config.getNetworkConfig().setPort(hazelcastPort);
        config.getProperties().setProperty("hazelcast.discovery.enabled", "true");
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
        EurekaOneDiscoveryStrategyFactory discoveryStrategyFactory = new EurekaOneDiscoveryStrategyFactory();
        Map<String, Comparable> properties = new HashMap<String, Comparable>();
        properties.put("self-registration", "true");
        properties.put("namespace", "hazelcast");
        properties.put("use-metadata-for-host-and-port", "true");
        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(discoveryStrategyFactory, properties);
        joinConfig.getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        return config;
    }

    @Bean public HazelcastInstance hazelcastInstance(Config hazelcastConfig) { return Hazelcast.newHazelcastInstance(hazelcastConfig); }

    @Bean
    public WebFilter webFilter(HazelcastInstance hazelcastInstance) {

        Properties properties = new Properties();
        properties.put("instance-name", hazelcastInstance.getName());
        properties.put("sticky-session", "false");

        return new WebFilter(properties);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
