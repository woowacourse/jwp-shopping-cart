package cart.auth;

import cart.dto.LoginDto;
import cart.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {

    private final BasicAuthorizationExtractor extractor;
    private final MemberService memberService;

    public BasicAuthInterceptor(BasicAuthorizationExtractor extractor, MemberService memberService) {
        this.extractor = extractor;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginDto extract = extractor.extract(request);
        if (extract == null) {
            throw new AuthorizationException();
        }
        if (!memberService.login(extract)) {
            throw new AuthorizationException();
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
