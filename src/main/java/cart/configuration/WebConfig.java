package cart.configuration;

import cart.argumentresolver.basicauthorization.BasicAuthorizationArgumentResolver;
import cart.interceptor.AuthInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicAuthorizationArgumentResolver basicAuthorizationArgumentResolver;
    private final AuthInterceptor authInterceptor;

    public WebConfig(
        final BasicAuthorizationArgumentResolver basicAuthorizationArgumentResolver,
        final AuthInterceptor authInterceptor
    ) {
        this.basicAuthorizationArgumentResolver = basicAuthorizationArgumentResolver;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/cart/*");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthorizationArgumentResolver);
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/cart").setViewName("cart");
    }
}
