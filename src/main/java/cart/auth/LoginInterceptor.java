package cart.auth;

import cart.auth.extractor.AuthorizationExtractor;
import cart.dto.auth.AuthInfo;
import cart.exception.AuthorizationException;
import cart.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION = "Authorization";

    private final MemberService memberService;

    public LoginInterceptor(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override

    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        String header = request.getHeader(AUTHORIZATION);
        validateHeader(header);
        AuthInfo authInfo = extractAuthInfoByHeader(request, header);
        validateAuthInfo(authInfo);

        return true;
    }

    private AuthInfo extractAuthInfoByHeader(final HttpServletRequest request, final String header) {
        AuthorizationExtractor<AuthInfo> extractor = AuthType.getExtractor(header);
        return extractor.extract(request);
    }

    private void validateAuthInfo(final AuthInfo authInfo) {
        if (!memberService.existsMember(authInfo)) {
            throw new AuthorizationException("존재하지 않는 사용자입니다.");
        }
    }

    private void validateHeader(final String header) {
        if (header == null) {
            throw new AuthorizationException("사용자 인증이 필요합니다.");
        }
    }
}
