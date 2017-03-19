package spring.boot.learn.env.launch;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.boot.learn.bean.usage.SimpleService;
//Use @ComponentScan to bootstrap @Configuration classes
//@ComponentScan(basePackageClasses={MinSpringEnvApp2.class})
@ComponentScan
public class MinSpringEnvWithComponentScan {
	
	@Configuration
	public static class BeanConfig{
		@Bean
		SimpleService createService() {
			return new SimpleService();
		}

	}


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MinSpringEnvWithComponentScan.class);
		// Disable web
		app.setWebEnvironment(false);

		ConfigurableApplicationContext context = app.run(args);

		SimpleService simpleService = context.getBean(SimpleService.class);
		System.out.println(simpleService);
	}

}
