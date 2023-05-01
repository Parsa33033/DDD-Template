package org.example.application.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

  private static Logger getLogger(ProceedingJoinPoint joinPoint) {
    return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
  }

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) ||" +
      "within(@org.springframework.stereotype.Service *) ||" +
      "within(@org.springframework.stereotype.Repository *) ||" +
      "within(@org.springframework.stereotype.Controller *)")
  public void beanPointcut() {
  }

  @Pointcut("within(org.example.application.controllers..*)")
  public void packagePointcut() {
  }

  @Around("beanPointcut() && packagePointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    Logger logger = getLogger(joinPoint);
    Long t1 = System.currentTimeMillis();
    if (logger.isDebugEnabled()) {
      logger.debug("-> Entered: {} with: ", joinPoint.getSignature().getName(), Arrays.asList(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (logger.isDebugEnabled()) {
        logger.debug("-> Exited {} in {}ms with result: {}", joinPoint.getSignature().getName(), System.currentTimeMillis() - t1, result);
      } else {
        logger.info("-> Exited {} in {}ms with args: {}, and returned: {}",
            joinPoint.getSignature().getName(), System.currentTimeMillis() - t1,
            Arrays.asList(joinPoint.getArgs()),
            result);
      }
      return result;
    } catch (Throwable t) {
      logger.debug("-> Error");
      throw t;
    }
  }
}