package woowacourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.domain.BearerExtractor;
import woowacourse.auth.domain.TokenProvider;
import woowacourse.config.interceptor.LoginMemberInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final TokenProvider tokenProvider;
    private final BearerExtractor bearerExtractor;

    public InterceptorConfig(TokenProvider tokenProvider, BearerExtractor bearerExtractor) {
        this.tokenProvider = tokenProvider;
        this.bearerExtractor = bearerExtractor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginMemberInterceptor())
                .addPathPatterns("/api/customer/**");
    }

    @Bean
    public LoginMemberInterceptor loginMemberInterceptor() {
        return new LoginMemberInterceptor(tokenProvider, bearerExtractor);
    }

}
