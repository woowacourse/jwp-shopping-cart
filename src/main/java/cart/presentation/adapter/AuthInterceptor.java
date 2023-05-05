package cart.presentation.adapter;

import cart.business.service.MemberService;
import cart.presentation.dto.AuthInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    public AuthInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AuthInfo authInfo = BasicAuthorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new IllegalArgumentException("올바르지 않은 인증 정보입니다.");
        }
        Integer memberId = memberService.findAndReturnId(DomainConverter.toMemberWithoutId(authInfo));
        memberService.validateExists(memberId);
        return true;
    }
}
