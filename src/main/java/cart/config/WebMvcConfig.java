package cart.config;

import cart.infrastructure.BasicAuthInterceptor;
import cart.infrastructure.PrincipalResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final PrincipalResolver principalResolver;
    private final BasicAuthInterceptor basicAuthInterceptor;

    public WebMvcConfig(PrincipalResolver principalResolver, BasicAuthInterceptor basicAuthInterceptor) {
        this.principalResolver = principalResolver;
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(principalResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/api/cart/**");
    }
}
