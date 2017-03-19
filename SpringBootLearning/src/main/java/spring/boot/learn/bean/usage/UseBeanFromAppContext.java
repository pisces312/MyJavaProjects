package spring.boot.learn.bean.usage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Use bean by context.getBean in main thread
 * 
 * Can not use @Autowired bean in static method
 * 
 * @author nil4
 *
 */
@SpringBootApplication

public class UseBeanFromAppContext {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(UseBeanFromAppContext.class, args);) {
			SimpleService simpleService = context.getBean(SimpleService.class);
			System.out.println(simpleService);
		}

	}

}
