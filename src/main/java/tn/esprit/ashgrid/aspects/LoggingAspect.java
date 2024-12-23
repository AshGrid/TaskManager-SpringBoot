package tn.esprit.ashgrid.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* tn.esprit.ashgrid.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();
        log.info("In Method : " + name) ;
    }

    @After("execution(* tn.esprit.ashgrid.services.*.*(..))")
    public void logAfter(JoinPoint joinPoint)
    {
        String name = joinPoint.getSignature().getName();
        log.info("Out Method : " + name) ;
    }


}
