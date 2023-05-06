package cart.config;

import cart.argumentresolver.AuthenticatedMemberArgumentResolver;
import cart.interceptor.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicAuthInterceptor basicAuthInterceptor;

    public WebConfig(final BasicAuthInterceptor basicAuthInterceptor) {
        this.basicAuthInterceptor = basicAuthInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor).addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticatedMemberArgumentResolver());
    }
}
