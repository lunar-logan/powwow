package io.powwow.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

import java.net.http.HttpClient;
import java.time.Duration;

@SpringBootApplication(scanBasePackages = "io.powwow")
public class PowwowServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PowwowServer.class).build().run(args);
    }

    @Bean
    public Jedis redisClientBean() {
        return new Jedis(new HostAndPort("localhost", 6379));
    }

    @Bean
    public HttpClient defaultHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(3030))
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
    }
}
