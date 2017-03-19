package spring.boot.learn.bean.usage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Use bean in CommandLineRunner.run method
 * 
 * @author nil4
 *
 */
@SpringBootApplication

public class UseBeanFromAutowired implements CommandLineRunner {
    @Autowired
    private SimpleService simpleService;

    public static void main(String[] args) {
        SpringApplication.run(UseBeanFromAutowired.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        simpleService.func();
//        System.out.println(simpleService);

    }

}
