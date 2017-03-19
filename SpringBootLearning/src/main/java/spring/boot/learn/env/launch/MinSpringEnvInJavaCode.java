package spring.boot.learn.env.launch;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import spring.boot.learn.bean.usage.SimpleService;

//Not introduce any new @Configuration or @Component classes
//No bootstrap annotation
//Need to register bean in Java code
public class MinSpringEnvInJavaCode {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MinSpringEnvInJavaCode.class);
		// Disable web
		app.setWebEnvironment(false);
		ConfigurableApplicationContext context = app.run(args);
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SimpleService.class);

		BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) context.getBeanFactory();
		beanDefinitonRegistry.registerBeanDefinition("myBean", beanDefinitionBuilder.getRawBeanDefinition());

		SimpleService simpleService = context.getBean(SimpleService.class);
		System.out.println(simpleService);
	}

}
