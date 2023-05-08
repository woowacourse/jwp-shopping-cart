package cart.auth;

import cart.dto.AuthInfo;
import cart.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthenticationService authenticationService;
    private final MemberService memberService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            throw new UnAuthenticationException();
        }

        final AuthInfo authInfo = authenticationService.decryptBasic(header);
        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();

        if (!authenticationService.canLogin(email, password)) {
            throw new UnAuthenticationException();
        }

        request.setAttribute("member", memberService.find(email, password));

        return true;
    }
}
