package cart.common.auth;

import cart.domain.MemberRole;
import cart.exception.ForbiddenException;
import cart.service.MemberService;
import cart.service.dto.MemberResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final String DELIMITER = ":";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final MemberService memberService;

    public AdminAuthInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        final String authorization = request.getHeader(AUTHORIZATION_HEADER);
        final String memberToken = BasicTokenProvider.extractToken(authorization);
        final String memberEmail = memberToken.split(DELIMITER)[0];
        final String memberPassword = memberToken.split(DELIMITER)[1];
        final MemberResponse memberResponse = memberService.getByEmailAndPassword(memberEmail, memberPassword);
        boolean isAdmin = MemberRole.isAdmin(memberResponse.getRole());
        if (!isAdmin) {
            throw new ForbiddenException();
        }
        return true;
    }
}
