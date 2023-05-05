package cart.authorization;

import cart.exception.UnauthorizedMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLoginInterceptor implements HandlerInterceptor {

    static final String AUTH_INFO = "memberId";

    private final AuthorizationService authorizationService;

    public MemberLoginInterceptor(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long memberId;
        try {
            memberId = authorizationService.authorizeMember(request);
        } catch (UnauthorizedMemberException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        request.setAttribute(AUTH_INFO, memberId);
        return true;
    }
}
