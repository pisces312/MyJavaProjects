package spring.boot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AspectJAdvice {
    @DeclareParents(value = "spring.boot.aop.SayHello",
        defaultImpl = spring.boot.aop.IntroductionOk.class)
    public Ok ok;

    @After(value = "execution(* spring.boot.aop.Hello.sayHelloAfter(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "execution(* spring.boot.aop.Hello.sayHelloAfterReturning(..))",
        returning = "retVal")
    public void afterReturningAdvice(JoinPoint joinPoint, String retVal) {
        System.out.println("AfterReturning: " + joinPoint.getSignature().getName());
        System.out.println("Return Value: " + retVal);
    }

    @AfterThrowing(value = "execution(* spring.boot.aop.Hello.sayHelloAfterThrowing(..))",
        throwing = "e")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        System.out.println("AfterThrowing: " + joinPoint.getSignature().getName());
        System.out.println("Exception Message: " + e.getMessage());
    }

    @Around(value = "execution(* spring.boot.aop.Hello.sayHelloAround(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around: " + joinPoint.getSignature().getName());
        System.out.println("Before");
        Object obj = joinPoint.proceed();
        System.out.println("End");
        return obj;
    }

    @Before(value = "execution(* spring.boot.aop.Hello.sayHelloBefore(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature().getName());
    }
}
