package cart.authentication;

import cart.dao.member.MemberDao;
import cart.entity.member.Member;
import cart.exception.notfound.MemberNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticateInterceptor implements HandlerInterceptor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    private final MemberDao memberDao;
    private final AuthorizationExtractor authorizationExtractor;

    public AuthenticateInterceptor(final MemberDao memberDao, final AuthorizationExtractor authorizationExtractor) {
        this.memberDao = memberDao;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        MemberInfo memberInfo = authorizationExtractor.extract(request);
        Member member = memberDao.findByEmailAndPassword(memberInfo.getEmail(), memberInfo.getPassword())
            .orElseThrow(MemberNotFoundException::new);

        return true;
    }
}
