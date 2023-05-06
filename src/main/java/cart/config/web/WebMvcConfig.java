package cart.config.web;

import cart.config.auth.BasicAuthArgumentResolver;
import cart.config.auth.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final BasicAuthInterceptor basicAuthInterceptor;
    private final BasicAuthArgumentResolver basicAuthArgumentResolver;

    public WebMvcConfig(final BasicAuthInterceptor basicAuthInterceptor, final BasicAuthArgumentResolver basicAuthArgumentResolver) {
        this.basicAuthInterceptor = basicAuthInterceptor;
        this.basicAuthArgumentResolver = basicAuthArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthArgumentResolver);
    }
}
