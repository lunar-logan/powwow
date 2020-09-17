package io.powwow;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "io.powwow")
@AllArgsConstructor
public class Powwow {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Powwow.class).build().run(args);
    }
}
