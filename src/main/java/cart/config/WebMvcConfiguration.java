package cart.config;

import cart.auth.AuthenticationHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthenticationHandlerInterceptor authenticationHandlerInterceptor;

    public WebMvcConfiguration(final AuthenticationHandlerInterceptor authenticationHandlerInterceptor) {
        this.authenticationHandlerInterceptor = authenticationHandlerInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationHandlerInterceptor)
                .addPathPatterns("/cart/products/**");
    }
}
