package cart.config;

import cart.controller.auth.BasicAuthorizeInterceptor;
import cart.controller.auth.LoginIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final BasicAuthorizeInterceptor basicAuthorizeInterceptor;

    public AuthConfig(final BasicAuthorizeInterceptor basicAuthorizeInterceptor) {
        this.basicAuthorizeInterceptor = basicAuthorizeInterceptor;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginIdArgumentResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthorizeInterceptor)
                .addPathPatterns("/cart/products/**");
    }
}
