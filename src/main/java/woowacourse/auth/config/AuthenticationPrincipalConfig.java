package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.ui.AuthInterceptor;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;
import woowacourse.shoppingcart.dao.CustomerDao;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final CustomerDao customerDao;

    public AuthenticationPrincipalConfig(final AuthService authService, final CustomerDao customerDao) {
        this.authService = authService;
        this.customerDao = customerDao;
    }

    @Override
    public void addArgumentResolvers(final List argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/users/me")
                .excludePathPatterns("/login")
                .excludePathPatterns("/api");
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(authService, customerDao);
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor(authService);
    }
}
