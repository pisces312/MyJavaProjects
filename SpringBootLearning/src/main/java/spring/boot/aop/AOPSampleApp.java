package spring.boot.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// @EnableAutoConfiguration
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AOPSampleApp {
    public static void main(String[] args) throws Exception {
        try (ConfigurableApplicationContext context =
            SpringApplication.run(AOPSampleApp.class, args);) {

            //        Hello hello = context.getBean(Hello.class);
            Hello hello = context.getBean(SayHello.class);

            System.out.println("=====Before Advice Begin=====");
            hello.sayHelloBefore();
            System.out.println("=====Before Advice End=====");

            System.out.println("=====After Advice Begin=====");
            hello.sayHelloAfter();
            System.out.println("=====After Advice End=====");

            System.out.println("=====After Returning Advice Begin=====");
            hello.sayHelloAfterReturning();
            System.out.println("=====After Returning Advice End=====");

            System.out.println("=====Around Advice Begin=====");
            hello.sayHelloAround();
            System.out.println("=====Around Advice End=====");

            System.out.println("=====Throws Advice Begin=====");
            try {
                hello.sayHelloAfterThrowing();
            } catch (Exception e) {
                //            assertNotNull(e);
            }
            System.out.println("=====Throws Advice End=====");

            System.out.println("=====Introduction Begin=====");
            // 由于对Hello接口进行了引入，使得实现了Hello接口的类可以具备Ok接口的功能
            hello.sayHelloIntroduction();
            System.out.println(hello.getClass().getName());
            ((Ok) hello).sayOk();
            System.out.println("=====Introduction End=====");
        }
    }
}
