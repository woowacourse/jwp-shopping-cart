package cart.config;

import cart.auth.BasicAuthArgumentResolver;
import cart.auth.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicAuthArgumentResolver basicAuthArgumentResolver;
    private final BasicAuthInterceptor basicAuthInterceptor;

    public WebConfig(final BasicAuthArgumentResolver basicAuthArgumentResolver, final BasicAuthInterceptor basicAuthInterceptor) {
        this.basicAuthArgumentResolver = basicAuthArgumentResolver;
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/user/cart/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthArgumentResolver);
    }
}
