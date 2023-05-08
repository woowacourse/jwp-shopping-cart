package cart.authentication;

import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
import cart.infrastructure.AuthorizationExtractor;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final MemberService memberService;

    public LoginInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authInfo = authorizationExtractor.extract(request);

        validateMember(authInfo);

        return true;
    }

    private void validateMember(AuthInfo authInfo) {
        if (authInfo == null) {
            throw new AuthorizationException("권한이 없는 사용자입니다.");
        }

        if (!memberService.isExistMember(authInfo.getEmail())) {
            throw new AuthorizationException("존재하지 않는 사용자입니다.");
        }
    }
}
