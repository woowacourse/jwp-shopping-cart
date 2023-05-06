package cart.config;

import cart.auth.AuthArgumentResolver;
import cart.auth.LoginInterceptor;
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

    public CartWebConfiguration(MembersService membersService) {
        this.membersService = membersService;
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(membersService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/api/cart/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }
}
