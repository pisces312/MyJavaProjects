package spring.boot.simpleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
@EnableAutoConfiguration
public class EnableAutoConfigureAnnotatedApp {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(EnableAutoConfigureAnnotatedApp.class, args)));
    }
}
