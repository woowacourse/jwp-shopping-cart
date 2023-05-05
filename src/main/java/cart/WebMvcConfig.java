package cart;

import cart.authorization.AuthorizationService;
import cart.authorization.AuthorizedMemberArgumentResolver;
import cart.authorization.MemberLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationService authorizationService;

    @Autowired
    public WebMvcConfig(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/cart")
                .setViewName("cart");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final HandlerInterceptor interceptor = new MemberLoginInterceptor(authorizationService);

        registry.addInterceptor(interceptor)
                .addPathPatterns("/cart/items/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthorizedMemberArgumentResolver());
    }
}
