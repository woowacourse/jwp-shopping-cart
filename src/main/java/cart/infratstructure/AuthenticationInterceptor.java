package cart.infratstructure;

import static cart.infratstructure.AuthenticationAttribute.LOGIN_MEMBER_ID;

import cart.domain.member.Member;
import cart.domain.member.MemberService;
import cart.dto.AuthInfo;
import cart.exception.AuthenticationException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String EXCEPTION_MESSAGE_MEMBER_NOT_FOUND = "사용자 정보가 존재하지 않습니다";
    private static final String EXCEPTION_MESSAGE_WRONG_AUTH_INFO = "잘못된 사용자 정보입니다";

    private final AuthorizationExtractor authorizationExtractor;
    private final MemberService memberService;

    public AuthenticationInterceptor(final AuthorizationExtractor authorizationExtractor,
                                     final MemberService memberService) {
        this.authorizationExtractor = authorizationExtractor;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        AuthInfo authInfo = extractAutoInfo(request);
        Long loginMemberId = findLoginMemberId(authInfo.getEmail(), authInfo.getPassword());
        
        request.setAttribute(LOGIN_MEMBER_ID.name(), loginMemberId);
        return true;
    }

    private AuthInfo extractAutoInfo(final HttpServletRequest request) {
        AuthInfo authInfo = authorizationExtractor.extract(request);
        if (authInfo == null) {
            throw new AuthenticationException(EXCEPTION_MESSAGE_MEMBER_NOT_FOUND);
        }
        return authInfo;
    }

    private Long findLoginMemberId(final String email, final String password) {
        Member member = memberService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(EXCEPTION_MESSAGE_WRONG_AUTH_INFO));
        if (!Objects.equals(password, member.getPassword())) {
            throw new AuthenticationException(EXCEPTION_MESSAGE_WRONG_AUTH_INFO);
        }
        return member.getId();
    }
}
