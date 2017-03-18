package spring.boot.aop;

import org.springframework.stereotype.Component;

@Component
public class SayHello implements Hello {
    public void sayHelloBefore() {
        System.out.println("Say Hello Before!");
    }

    public void sayHelloAfter() {
        System.out.println("Say Hello After!");
    }


    public String sayHelloAfterReturning() {
        System.out.println("Say Hello AfterReturning!");
        return "Hello";
    }

    public void sayHelloAround() {
        System.out.println("Say Hello Around!");
    }



    public void sayHelloIntroduction() {
        System.out.println("Say Hello Introduction!");
    }

    public void sayHelloAfterThrowing() {
        System.out.println("Say Hello After Throwing!");
        throw new RuntimeException("Hello Exception");
    }
}
