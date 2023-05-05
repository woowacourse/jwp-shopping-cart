package cart.configure;

import cart.authorization.BasicAuthorizationArgumentResolver;
import cart.authorization.BasicAuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    private final BasicAuthorizationInterceptor basicAuthorizationInterceptor;

    public WebConfigurer(BasicAuthorizationInterceptor basicAuthorizationInterceptor) {
        this.basicAuthorizationInterceptor = basicAuthorizationInterceptor;
    }
    @Override
    public void addInterceptors(final InterceptorRegistry registry){
        registry.addInterceptor(basicAuthorizationInterceptor)
                .addPathPatterns("/carts/**");
    }
    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(new BasicAuthorizationArgumentResolver());
    }
}
