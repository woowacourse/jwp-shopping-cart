package cart.auth;

import cart.dao.MemberDao;
import cart.domain.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_TYPE = "Basic ";
    private final AuthContext authContext;
    private final MemberDao memberDao;

    public AuthInterceptor(final AuthContext authContext, final MemberDao memberDao) {
        this.authContext = authContext;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        final String token = getBasicToken(request);
        final String[] authInformation = getAuthInformation(token);
        final String email = authInformation[0];
        final Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("해당 이메일로 가입된 회원이 없습니다"));
        final String password = authInformation[1];
        member.login(password);
        authContext.setAuthMember(member);
        return true;
    }

    private String getBasicToken(final HttpServletRequest request) {
        final String token = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(token)) {
            throw new AuthenticationException("Authorization 헤더가 없습니다.");
        }
        if (!token.startsWith(BASIC_TYPE)) {
            throw new AuthenticationException("토큰의 형식은 Basic 이어야 합니다.");
        }
        return token.replaceFirst(BASIC_TYPE, "");
    }

    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(":");
    }
}
