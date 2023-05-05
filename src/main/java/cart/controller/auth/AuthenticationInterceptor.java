package cart.controller.auth;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import cart.controller.auth.dto.AuthInfo;
import cart.controller.auth.exception.AuthenticateFailException;
import cart.controller.auth.exception.InvalidAuthenticationException;
import cart.controller.auth.exception.EmptyAuthenticationException;
import cart.dao.UserDao;
import cart.domain.User;
import cart.infra.BasicAuthorizationExtractor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private final UserDao userDao;

    public AuthenticationInterceptor(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        AuthInfo authInfo = BasicAuthorizationExtractor.extract(request);
        validateHas(authInfo);
        validateHasEmailAndPasswordIn(authInfo);

        try {
            final User user = userDao.selectBy(authInfo.getEmail());
            validatePasswordMatches(authInfo, user);
        } catch (EmptyResultDataAccessException exception) {
            throw new InvalidAuthenticationException();
        }

        return true;
    }

    private void validateHas(AuthInfo authInfo) {
        if (Objects.isNull(authInfo)) {
            throw new EmptyAuthenticationException();
        }
    }

    private void validateHasEmailAndPasswordIn(AuthInfo authInfo) {
        if (!StringUtils.hasLength(authInfo.getEmail()) || !StringUtils.hasLength(authInfo.getPassword())) {
            throw new InvalidAuthenticationException();
        }
    }

    private void validatePasswordMatches(AuthInfo authInfo, User user) {
        if (!Objects.equals(authInfo.getPassword(), user.getPassword())) {
            throw new AuthenticateFailException();
        }
    }
}
