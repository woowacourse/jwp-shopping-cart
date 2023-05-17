package cart.auth;

import cart.auth.dto.AuthInfo;
import cart.business.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private MemberService memberService;
    private static BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    public AuthInterceptor(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        if (authService.checkInvalidLogin(email, password)) {
            throw new AuthenticationException("유효하지 않은 로그인 요청입니다.");
        }

        Integer memberId = memberService.findMemberByEmail(email).get().getId();

        request.setAttribute("member_id", memberId);
        request.setAttribute("email", email);
        request.setAttribute("password", password);

        return true;
    }
}
