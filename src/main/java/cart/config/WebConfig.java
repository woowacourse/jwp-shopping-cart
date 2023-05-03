package cart.config;

import cart.common.AuthInfoHandlerMethodArgumentResolver;
import cart.common.AuthenticationCheckInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInfoHandlerMethodArgumentResolver authInfoHandlerMethodArgumentResolver;
    private final AuthenticationCheckInterceptor authenticationCheckInterceptor;

    public WebConfig(
        final AuthInfoHandlerMethodArgumentResolver authInfoHandlerMethodArgumentResolver,
        final AuthenticationCheckInterceptor authenticationCheckInterceptor) {
        this.authInfoHandlerMethodArgumentResolver = authInfoHandlerMethodArgumentResolver;
        this.authenticationCheckInterceptor = authenticationCheckInterceptor;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authInfoHandlerMethodArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationCheckInterceptor)
            .addPathPatterns("/cart");
    }
}
