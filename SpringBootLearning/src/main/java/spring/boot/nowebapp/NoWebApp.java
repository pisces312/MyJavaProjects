package spring.boot.nowebapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// exclude datasource config
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NoWebApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NoWebApp.class);
        //Disable web
        app.setWebEnvironment(false);
        app.run(args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("run");
    }
}
