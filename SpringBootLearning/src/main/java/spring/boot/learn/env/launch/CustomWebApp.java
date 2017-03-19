package spring.boot.learn.env.launch;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
/**
 * Customize web container
 * @author nil4
 *
 */
@SpringBootApplication
public class CustomWebApp {

    public static void main(String[] args) {
        SpringApplication.run(CustomWebApp.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {

            @Override
            public void customize(Connector connector) {

                connector.setPort(9000);
//                connector.setAsyncTimeout(60000);
            }
        });
        return factory;
    }
}
