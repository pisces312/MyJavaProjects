package spring.boot.aop;

public interface Hello {

    void sayHelloBefore();

    void sayHelloAfter();

    String sayHelloAfterReturning();

    void sayHelloAfterThrowing();

    void sayHelloAround();

    void sayHelloIntroduction();

}
