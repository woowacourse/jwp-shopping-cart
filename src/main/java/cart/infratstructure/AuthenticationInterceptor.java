package cart.infratstructure;

import cart.domain.member.Member;
import cart.domain.member.MemberService;
import cart.dto.AuthInfo;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    // TODO 속성명 이넘으로 분리
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private final MemberService memberService;

    public AuthenticationInterceptor(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new AuthenticationException("사용자 정보가 존재하지 않습니다");
        }
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        Member member = memberService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("잘못된 사용자 정보입니다"));
        if (!Objects.equals(password, member.getPassword())) {
            throw new AuthenticationException("잘못된 사용자 정보입니다");
        }

        request.setAttribute(LOGIN_MEMBER_ID, member.getId());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
