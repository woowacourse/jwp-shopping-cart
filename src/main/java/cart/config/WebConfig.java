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

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new Base64AuthInterceptor())
                .addPathPatterns("/admin/products/**", "/carts/**")
                .order(Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(new Base64AdminAccessInterceptor())
                .addPathPatterns("/admin/products/**")
                .order(Ordered.LOWEST_PRECEDENCE);

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new Base64AuthArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
