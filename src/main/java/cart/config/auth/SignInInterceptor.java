package cart.config.auth;

import cart.service.MemberService;
import cart.service.dto.MemberAuthDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SignInInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    public SignInInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MemberAuthDto memberAuthDto = BasicTokenDecoder.extract(request);
        try {
            this.memberService.signIn(memberAuthDto);
            return true;
        } catch (IllegalArgumentException e) {
            throw new AuthenticationFailException(e.getMessage());
        }
    }
}
