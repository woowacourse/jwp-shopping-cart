package cart.config;

import cart.auth.BasicAuthArgumentResolver;
import cart.auth.BasicAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor())
                .addPathPatterns("/user/cart/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(basicAuthArgumentResolver());
    }

    @Bean
    public BasicAuthInterceptor basicAuthInterceptor() {
        return new BasicAuthInterceptor();
    }

    @Bean
    public BasicAuthArgumentResolver basicAuthArgumentResolver() {
        return new BasicAuthArgumentResolver();
    }
}
