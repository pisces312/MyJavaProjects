package spring.boot.simplewebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
// Optional, specify the init order
@Order(value = 1)
public class SimpleService implements CommandLineRunner {

    //Get context from Component
    @Autowired
    private ConfigurableApplicationContext context;

    //Use env to get key/value from PropertySource (properties)
    @Autowired
    Environment env;

    public SimpleService() {
        System.out.println("Non-parameter ctor");
    }

    public SimpleService(String str) {
        System.out.println("ctor with one parameter " + str);
    }

    public void func(){
        System.out.println("working");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Init for SimpleService");
        System.out.println("Access to context:"+context);

        System.out.println(env.getProperty("key1"));

    }

}
