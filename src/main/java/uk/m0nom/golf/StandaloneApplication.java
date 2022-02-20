package uk.m0nom.golf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableConfigurationProperties
public class StandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(StandaloneApplication.class, args);
    }
}