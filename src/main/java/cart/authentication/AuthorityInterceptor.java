package cart.authentication;

import cart.entity.member.Member;
import cart.exception.member.AuthorityException;
import cart.service.member.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    private final MemberService memberService;
    private final AuthorizationExtractor authorizationExtractor;

    public AuthorityInterceptor(final MemberService memberService, final AuthorizationExtractor authorizationExtractor) {
        this.memberService = memberService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        MemberInfo memberInfo = authorizationExtractor.extract(request);

        Member member = memberService.findByEmailAndPassword(memberInfo.getEmail(), memberInfo.getPassword());

        if (!member.isAdminUser()) {
            throw new AuthorityException();
        }

        return true;
    }
}
