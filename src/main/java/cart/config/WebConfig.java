package cart.config;

import cart.argumentresolver.BasicLoginArgumentResolver;
import cart.intercepter.BasicAuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BasicLoginArgumentResolver argumentResolver;
    private final BasicAuthorizationInterceptor interceptor;

    public WebConfig(BasicLoginArgumentResolver argumentResolver, BasicAuthorizationInterceptor interceptor) {
        this.argumentResolver = argumentResolver;
        this.interceptor = interceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/carts/**");
    }
}
