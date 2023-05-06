package cart.config;

import cart.common.auth.AuthInfoArgumentResolver;
import cart.common.auth.AuthenticationCheckInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInfoArgumentResolver authInfoArgumentResolver;
    private final AuthenticationCheckInterceptor authenticationCheckInterceptor;

    public WebConfig(
        final AuthInfoArgumentResolver authInfoArgumentResolver,
        final AuthenticationCheckInterceptor authenticationCheckInterceptor) {
        this.authInfoArgumentResolver = authInfoArgumentResolver;
        this.authenticationCheckInterceptor = authenticationCheckInterceptor;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authInfoArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationCheckInterceptor)
            .addPathPatterns("/api/cart");
    }
}
