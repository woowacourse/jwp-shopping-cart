package woowacourse.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowacourse.auth.ui.AuthInterceptor;
import woowacourse.auth.ui.AuthenticationPrincipalArgumentResolver;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private static final String PATH_API_MEMBERS_ALL = "/api/members/**";
    private static final String PATH_API_CARTS_ALL = "/api/carts/**";
    private static final String PATH_API_MEMBERS = "/api/members";
    private static final String PATH_API_MEMBERS_EMAIL_CHECK = "/api/members/email-check";

    private final AuthInterceptor authInterceptor;
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    public AuthenticationPrincipalConfig(AuthInterceptor authInterceptor,
                                         AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver) {
        this.authInterceptor = authInterceptor;
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(PATH_API_MEMBERS_ALL)
                .addPathPatterns(PATH_API_CARTS_ALL)
                .excludePathPatterns(PATH_API_MEMBERS, PATH_API_MEMBERS_EMAIL_CHECK);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationPrincipalArgumentResolver);
    }
}
