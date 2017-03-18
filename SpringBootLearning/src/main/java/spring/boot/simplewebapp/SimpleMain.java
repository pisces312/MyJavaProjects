package spring.boot.simplewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Import(value = AppConfig.class)
public class SimpleMain {
    public static void main(String[] args) {
        System.out.println("start");
        System.exit(SpringApplication.exit(SpringApplication.run(SimpleMain.class, args)));
        System.out.println("exit");
    }
}
