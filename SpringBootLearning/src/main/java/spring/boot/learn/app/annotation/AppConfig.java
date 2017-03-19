package spring.boot.learn.app.annotation;

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
@PropertySource("classpath:/spring/boot/learn/app/annotation/app.properties")
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
