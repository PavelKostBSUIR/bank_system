package com.vojtechruzicka.javafxweaverexample;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringBootExampleApplication {
    public static void main(String[] args) {
        // This is how normal Spring Boot app would be launched
        //SpringApplication.run(JavafxWeaverExampleApplication.class, args);

        Application.launch(JavaFxApplication.class, args);
    }
}
