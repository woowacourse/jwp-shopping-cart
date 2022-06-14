package woowacourse.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.MemberArgumentResolver;

import java.util.List;

@Configuration
public class MemberArgumentConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;

    public MemberArgumentConfig(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(final List argumentResolvers) {
        argumentResolvers.add(createMemberArgumentResolver());
    }

    @Bean
    public MemberArgumentResolver createMemberArgumentResolver() {
        return new MemberArgumentResolver(jwtTokenProvider);
    }
}
