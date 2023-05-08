package cart.config;

import cart.controller.advice.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(GlobalExceptionHandler.class);
    }
}
