package cart.controller.interceptor;

import cart.config.BasicAuthenticationExtractor;
import cart.dto.MemberAuthDto;
import cart.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final BasicAuthenticationExtractor basicAuthenticationExtractor;
    private final MemberService memberService;

    public AuthInterceptor(
            final BasicAuthenticationExtractor basicAuthenticationExtractor,
            final MemberService memberService
    ) {
        this.basicAuthenticationExtractor = basicAuthenticationExtractor;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String authorization = request.getHeader(AUTHORIZATION_HEADER_NAME);
        final MemberAuthDto memberAuthDto = basicAuthenticationExtractor.extract(authorization);
        memberService.findMember(memberAuthDto);
        return true;
    }
}
