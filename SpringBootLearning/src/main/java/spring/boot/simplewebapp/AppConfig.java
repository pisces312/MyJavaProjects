package spring.boot.simplewebapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
/**
 *Load to environmentProperties (PropertySource)
 *
 *  Use @Autowired Environment env to get key/value
 */
@PropertySource("classpath:/spring/boot/simplemain/app.properties")
public class AppConfig {

    /**
     * Optional
     * 
     * Use with PropertySourcesPlaceholderConfigurer bean
     * 
     * Can use place holder in spring
     * 
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer appConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
