package cart.config;

import cart.infrastructure.BasicAuthInterceptor;
import cart.infrastructure.IdConverter;
import cart.infrastructure.PrincipalArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final PrincipalArgumentResolver principalArgumentResolver;
    private final BasicAuthInterceptor basicAuthInterceptor;

    public WebMvcConfig(PrincipalArgumentResolver principalArgumentResolver, BasicAuthInterceptor basicAuthInterceptor) {
        this.principalArgumentResolver = principalArgumentResolver;
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(principalArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/api/cart/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new IdConverter());
    }
}
