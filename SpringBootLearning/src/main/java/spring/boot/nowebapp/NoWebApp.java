package spring.boot.nowebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/**
 * Not start web container
 * 
 * No data source configured
 * 
 * @author nili
 *
 */
// exclude datasource config
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NoWebApp  {

    public static void main(String[] args) {
    	System.out.println("start");
        SpringApplication app = new SpringApplication(NoWebApp.class);
        //Disable web
        app.setWebEnvironment(false);
        app.run(args);
        //No need to use SpringApplication.exit because no web container is launched
        System.out.println("exit");
    }
}
