package cart.auth;

import cart.domain.member.Member;
import cart.dto.AuthInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AuthenticationHandlerInterceptor implements HandlerInterceptor {

    private static final AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();

    private final AuthenticationService authenticationService;

    public AuthenticationHandlerInterceptor(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final Optional<AuthInfo> authInfo = Optional.ofNullable(authorizationExtractor.extract(request));
        if (authInfo.isEmpty()) {
            return false;
        }

        final Member member = authenticationService.login(authInfo.get().getEmail(), authInfo.get().getPassword());
        request.setAttribute("member", member);

        return true;
    }
}
