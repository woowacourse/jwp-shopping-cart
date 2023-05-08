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
    private final LoginIdArgumentResolver loginIdArgumentResolver;

    public AuthConfig(
            final BasicAuthorizeInterceptor basicAuthorizeInterceptor,
            final LoginIdArgumentResolver loginIdArgumentResolver
    ) {
        this.basicAuthorizeInterceptor = basicAuthorizeInterceptor;
        this.loginIdArgumentResolver = loginIdArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginIdArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthorizeInterceptor)
                .addPathPatterns("/cart/products/**");
    }
}
