package cart.auth;

import cart.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthenticationExtractor<MemberDto> authenticationExtractor;
    private final AuthenticationService authenticationService;

    public AuthenticationInterceptor(final AuthenticationExtractor<MemberDto> authenticationExtractor, final AuthenticationService authenticationService) {
        this.authenticationExtractor = authenticationExtractor;
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MemberDto extractedMember = authenticationExtractor.extract(request);
        authenticationService.checkAuthenticatedMember(extractedMember);
        return true;
    }
}
