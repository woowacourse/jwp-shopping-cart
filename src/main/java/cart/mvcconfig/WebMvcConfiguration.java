package cart.mvcconfig;

import cart.domain.member.MemberService;
import cart.infratstructure.AuthenticationInterceptor;
import cart.infratstructure.AuthenticationPrincipalArgumentResolver;
import cart.infratstructure.AuthorizationExtractor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthorizationExtractor authorizationExtractor;
    private final MemberService memberService;

    public WebMvcConfiguration(final AuthorizationExtractor authorizationExtractor, final MemberService memberService) {
        this.authorizationExtractor = authorizationExtractor;
        this.memberService = memberService;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(authorizationExtractor, memberService))
                .addPathPatterns("/cart-items/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }
}
