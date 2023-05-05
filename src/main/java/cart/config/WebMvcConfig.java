package cart.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.controller.auth.AuthenticationInterceptor;
import cart.controller.auth.AuthorizedUserIdArgumentResolver;
import cart.dao.UserDao;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserDao userDao;

    public WebMvcConfig(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(userDao))
                .addPathPatterns("/cart/items/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthorizedUserIdArgumentResolver(userDao));
    }
}
