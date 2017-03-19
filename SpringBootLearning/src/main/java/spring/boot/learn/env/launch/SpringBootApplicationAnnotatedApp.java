package spring.boot.learn.env.launch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SpringBootApplicationAnnotatedApp {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(SpringBootApplicationAnnotatedApp.class, args)));
    }
}
