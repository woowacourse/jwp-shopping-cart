package cart.auth;

import cart.dao.UserDao;
import cart.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    private static final String INVALID_USER_INFO = "잘못된 유저 정보입니다.";

    @Autowired
    private UserDao userDao;
    @Autowired
    private BasicAuthExtractor basicAuthExtractor;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String header = request.getHeader(AUTHORIZATION);
        final UserInfo userInfo = basicAuthExtractor.extract(header);

        final User user = userDao.findByEmail(userInfo.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(INVALID_USER_INFO));

        if (userInfo.isCorrect(user)) {
            return true;
        }

        throw new IllegalArgumentException(INVALID_USER_INFO);
    }
}
