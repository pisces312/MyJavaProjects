package spring.boot.simplewebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Use bean in CommandLineRunner.run method
 * 
 * @author nil4
 *
 */
@SpringBootApplication
//Component that not specified in basePackages will not be initialized
@ComponentScan(basePackages = {"spring.boot.simplemain"})
@Import(value = AppConfig.class)
public class CommandLineRunnerMain implements CommandLineRunner {
    @Autowired
    private SimpleService simpleService;

    public static void main(String[] args) {
        SpringApplication.run(CommandLineRunnerMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        simpleService.func();
//        System.out.println(simpleService);

    }

}
