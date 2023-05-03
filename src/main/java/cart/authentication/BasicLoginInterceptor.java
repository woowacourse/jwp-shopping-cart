package cart.authentication;

import cart.dao.MemberDao;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicLoginInterceptor implements HandlerInterceptor {

    private final MemberDao memberDao;

    public BasicLoginInterceptor(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        return memberDao.existsByEmail(authInfo.getEmail());
    }

}
