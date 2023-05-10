package cart.config;

import cart.config.admin.Base64AdminAccessInterceptor;
import cart.config.auth.Base64AuthArgumentResolver;
import cart.config.auth.Base64AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private Base64AuthInterceptor base64AuthInterceptor;
    private Base64AdminAccessInterceptor base64AdminAccessInterceptor;
    private Base64AuthArgumentResolver base64AuthArgumentResolver;

    public WebConfig(final Base64AuthInterceptor base64AuthInterceptor, final Base64AdminAccessInterceptor base64AdminAccessInterceptor, final Base64AuthArgumentResolver base64AuthArgumentResolver) {
        this.base64AuthInterceptor = base64AuthInterceptor;
        this.base64AdminAccessInterceptor = base64AdminAccessInterceptor;
        this.base64AuthArgumentResolver = base64AuthArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(base64AuthInterceptor)
                .addPathPatterns("/admin/products/**", "/carts/**")
                .order(Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(base64AdminAccessInterceptor)
                .addPathPatterns("/admin/products/**")
                .order(Ordered.LOWEST_PRECEDENCE);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(base64AuthArgumentResolver);
    }
}
