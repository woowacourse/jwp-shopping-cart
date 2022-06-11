package woowacourse.member.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.member.support.TokenManager;
import woowacourse.member.ui.AuthenticationPrincipalArgumentResolver;
import woowacourse.member.ui.LoginInterceptor;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;

    public AuthenticationPrincipalConfig(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(tokenManager))
                .addPathPatterns("/api/members/password-check")
                .addPathPatterns("/api/members/me")
                .addPathPatterns("/api/member/password")
                .addPathPatterns("/api/carts/**");
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(tokenManager);
    }
}
