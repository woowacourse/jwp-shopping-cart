package cart.common.auth;

import cart.controller.dto.MemberDto;
import cart.domain.MemberRole;
import cart.exception.ForbiddenException;
import cart.service.MemberService;
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
        final MemberDto memberDto = memberService.getByEmail(memberEmail);
        boolean isAdmin = MemberRole.isAdmin(memberDto.getRole());
        if (!isAdmin) {
            throw new ForbiddenException();
        }
        return true;
    }
}
