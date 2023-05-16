package cart.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.argumentresolver.AuthArgumentResolver;
import cart.interceptor.AuthInterceptor;
import cart.argumentresolver.CarRequestArgumentResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthInterceptor authInterceptor = new AuthInterceptor();
        registry.addInterceptor(authInterceptor).addPathPatterns("/cart-items/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
        resolvers.add(new CarRequestArgumentResolver());
    }
}
