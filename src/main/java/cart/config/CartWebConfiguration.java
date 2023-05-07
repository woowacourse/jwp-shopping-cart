package cart.config;

import cart.auth.AuthArgumentResolver;
import cart.auth.LoginInterceptor;
import cart.persistence.MembersDao;
import cart.service.MembersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CartWebConfiguration implements WebMvcConfigurer {

    private final MembersService membersService;
    private final MembersDao membersDao;

    public CartWebConfiguration(MembersService membersService, MembersDao membersDao) {
        this.membersService = membersService;
        this.membersDao = membersDao;
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(membersService);
    }

    @Bean
    public AuthArgumentResolver authArgumentResolver() {
        return new AuthArgumentResolver(membersDao);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/cart/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver());
    }
}
