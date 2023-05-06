package cart.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AuthenticationArgumentResolver argumentResolver;

    public WebMvcConfiguration(LoginInterceptor loginInterceptor, AuthenticationArgumentResolver argumentResolver) {
        this.loginInterceptor = loginInterceptor;
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }
}
