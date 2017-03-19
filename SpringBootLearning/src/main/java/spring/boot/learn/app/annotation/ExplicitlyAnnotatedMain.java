package spring.boot.learn.app.annotation;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

/**
 * Show all available annotations for Main explicitly
 *
 */
// Required:
@SpringBootApplication
// Or @EnableAutoConfiguration
//
// Optional:
// Component that not specified in basePackages will not be initialized
// Default is current package
@ComponentScan(basePackages = { "spring.boot.learn.app.annotation" })
// Can be scanned automatically
@Import(value = AppConfig.class)

public class ExplicitlyAnnotatedMain implements CommandLineRunner {

	@Autowired
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(ExplicitlyAnnotatedMain.class, args)));
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("===============Properties in app's property file================");

		PropertySourcesPlaceholderConfigurer configurer = context.getBean(PropertySourcesPlaceholderConfigurer.class);
		// System.out.println(configurer);
		PropertySources propertySources = configurer.getAppliedPropertySources();
		for (PropertySource<?> propertySource : propertySources) {
			System.out.println(propertySource);
			System.out.println(propertySource.getProperty("key1"));
		}
		// System.out.println(configurer.getAppliedPropertySources());

		System.out.println("\n\n==============System properties=================");
		Map<String, String> envs = System.getenv();
		envs.forEach((Object key, Object value) -> System.out.println(key + " = " + value));

		System.out.println("\n\n==============System properties=================");
		Properties props = System.getProperties();
		props.forEach((Object key, Object value) -> System.out.println(key + " = " + value));

	}

}
