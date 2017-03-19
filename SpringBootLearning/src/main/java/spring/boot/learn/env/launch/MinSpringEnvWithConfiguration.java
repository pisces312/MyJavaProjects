package spring.boot.learn.env.launch;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.boot.learn.bean.usage.SimpleService;
//Main and @Configuration classes are the same one
@Configuration
public class MinSpringEnvWithConfiguration {

	@Bean
	SimpleService createService() {
		return new SimpleService();
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MinSpringEnvWithConfiguration.class);
		// Disable web
		app.setWebEnvironment(false);

		ConfigurableApplicationContext context = app.run(args);

		SimpleService simpleService = context.getBean(SimpleService.class);
		System.out.println(simpleService);
	}

}
