package cart.config;

import cart.config.auth.SignInInterceptor;
import cart.config.log.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;
    private final SignInInterceptor signInInterceptor;

    public WebMvcConfiguration(LoggerInterceptor loggerInterceptor, SignInInterceptor signInInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
        this.signInInterceptor = signInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(signInInterceptor)
                .addPathPatterns("/products/**");
    }
}
