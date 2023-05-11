package cart.common.config;

import cart.auth.dao.UserDAO;
import cart.auth.dto.UserInfo;
import cart.auth.infrastructure.BasicAuthorizationExtractor;
import cart.common.exceptions.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    public static final String INVALID_USER_INFO_ERROR = "존재하지 않는 유저입니다.";
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private final UserDAO userDAO;

    public LoginInterceptor(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        final List<String> credentials = this.basicAuthorizationExtractor.extract(request);
        final UserInfo userInfo = UserInfo.of(credentials.get(0), credentials.get(1));
        if (!this.userDAO.isExist(userInfo)) {
            throw new AuthorizationException(INVALID_USER_INFO_ERROR);
        }
        return true;
    }
}
