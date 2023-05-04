package cart.controller.config;

import cart.controller.config.argumentresolver.BasicAuthorizationArgumentResolver;
import cart.controller.config.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class Config implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public Config(final AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns(
                        "/",
                        "/settings",
                        "/items/**",
                        "/cart",
                        "/css/**",
                        "/js/**"
                );
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new BasicAuthorizationArgumentResolver());
    }
}
