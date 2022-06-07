package woowacourse.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogTraceAspect {

    @Before("execution(* woowacourse.config.ControllerExceptionHandler.*(..)) &&  args(ex,..)")
    public void execute(JoinPoint joinPoint, Exception ex) {
        log.error("[error log] signature = {}", joinPoint.getSignature(), ex);
    }
}
