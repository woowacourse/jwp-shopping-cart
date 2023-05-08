package cart.auth;

import cart.dao.UserDao;
import cart.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    private static final String INVALID_USER_INFO = "잘못된 유저 정보입니다.";

    private final UserDao userDao;
    private final BasicAuthExtractor basicAuthExtractor;

    public BasicAuthInterceptor(final UserDao userDao, final BasicAuthExtractor basicAuthExtractor) {
        this.userDao = userDao;
        this.basicAuthExtractor = basicAuthExtractor;
    }

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
