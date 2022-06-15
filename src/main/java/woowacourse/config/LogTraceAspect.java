package woowacourse.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogTraceAspect {

    @Before("execution(* woowacourse.config.ApplicationExceptionHandler.*(..)) &&  args(ex,..)")
    public void logHandlerError(JoinPoint joinPoint, Exception ex) {
        log.error("[error log] signature = {}", joinPoint.getSignature(), ex);
    }

    @AfterThrowing(value = "execution(* woowacourse.shoppingcart.dao..*(..))", throwing = "ex")
    public void logDaoError(JoinPoint joinPoint, Exception ex) {
        log.error("[error log] signature = {}", joinPoint.getSignature(), ex);
    }
}
