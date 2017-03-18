package spring.boot.simplewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Use bean by context.getBean in main thread
 * 
 * @author nil4
 *
 */
@SpringBootApplication
//Component that not specified in basePackages will not be initialized
@ComponentScan(basePackages = {"spring.boot.simplemain"})
@Import(value = AppConfig.class)
public class SimpleSpringBootMain {
    /**
     * Can not use @Autowired bean in static method
     * 
     * but can use Context getBean
     * 
     * @param args
     */
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(SimpleSpringBootMain.class, args);) {

            //////////////////////////////////////////////////
            // Spring only use non-parameter ctor
            SimpleService simpleService = context.getBean(SimpleService.class);
            System.out.println(simpleService);


            // SimpleService simpleService2 = context.getBean(SimpleService.class, "test");
            // System.out.println(simpleService2);

            // System.out.println(System.getProperties());
            //            Map<String, String> envs = System.getenv();
            //            System.out.println(envs);


            //            PropertySourcesPlaceholderConfigurer configurer=context.getBean(PropertySourcesPlaceholderConfigurer.class);
            ////            System.out.println(configurer);
            //            PropertySources propertySources=configurer.getAppliedPropertySources();
            //            for(PropertySource<?> propertySource:propertySources){
            //                System.out.println(propertySource);
            //                System.out.println(propertySource.getProperty("key1"));
            //            }

            //            System.out.println(configurer.getAppliedPropertySources());

        }

    }

}
