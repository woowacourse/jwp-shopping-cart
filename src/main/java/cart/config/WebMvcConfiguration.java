package cart.config;

import cart.config.auth.BasicMemberResolver;
import cart.config.auth.SignInInterceptor;
import cart.config.log.LoggerInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
                .addPathPatterns("/products/**")
                .addPathPatterns("/carts/me/**")
                .excludePathPatterns("/carts/me");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new BasicMemberResolver());
    }
}
