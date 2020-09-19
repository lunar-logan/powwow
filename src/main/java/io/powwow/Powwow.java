package io.powwow;

import io.powwow.application.clusterlock.ClusterLockService;
import io.powwow.application.clusterlock.RedisClusterLockServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

@SpringBootApplication(scanBasePackages = "io.powwow")
@AllArgsConstructor
public class Powwow {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Powwow.class).build().run(args);
    }

    @Bean
    public ClusterLockService getClusterLockServiceBean() {
        return new RedisClusterLockServiceImpl(new Jedis(new HostAndPort("localhost", 5432)));
    }
}
