package cart.mvcconfig;

import cart.infrastructure.AuthInfoArgumentResolver;
import cart.infrastructure.BasicAuthorizationExtractor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/cart")
                .setViewName("cart");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final HandlerInterceptor interceptor = new LoginInterceptor(new BasicAuthorizationExtractor());

        registry.addInterceptor(interceptor)
                .addPathPatterns("/cart/items/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthInfoArgumentResolver());
    }
}
