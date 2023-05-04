package cart.web.argumentResolver;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cart.domain.member.MemberService;
import cart.web.cart.controller.AuthorizationExtractor;
import cart.web.cart.dto.AuthInfo;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final MemberService memberService;

    public WebMvcConfiguration(final AuthorizationExtractor<AuthInfo> authorizationExtractor,
        final MemberService memberService) {
        this.authorizationExtractor = authorizationExtractor;
        this.memberService = memberService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver(authorizationExtractor, memberService));
    }
}
