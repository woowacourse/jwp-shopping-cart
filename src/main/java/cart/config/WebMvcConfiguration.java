package cart.config;

import java.util.List;

import cart.controller.auth.AuthenticationPrincipalArgumentResolver;
import cart.controller.auth.LoginInterceptor;
import cart.service.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthService authService;

    public WebMvcConfiguration(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/carts/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver(authService));
    }
}
