package cart.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.controller.argumentresolver.AuthenticationPrincipalArgumentResolver;
import cart.controller.argumentresolver.BasicAuthDecoder;
import cart.controller.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/cart/products/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver(new BasicAuthDecoder()));
    }
}
