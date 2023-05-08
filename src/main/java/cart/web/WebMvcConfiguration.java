package cart.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.service.AuthService;
import cart.web.argumentResolver.AuthenticationArgumentResolver;
import cart.web.cart.dto.AuthInfo;
import cart.web.interceptor.AuthorizationExtractor;
import cart.web.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final AuthService authService;

    public WebMvcConfiguration(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final AuthService authService) {
        this.authorizationExtractor = authorizationExtractor;
        this.authService = authService;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/cart").setViewName("cart");
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final HandlerInterceptor loginInterceptor = new LoginInterceptor(authorizationExtractor, authService);
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/cart-products/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver());
    }
}
