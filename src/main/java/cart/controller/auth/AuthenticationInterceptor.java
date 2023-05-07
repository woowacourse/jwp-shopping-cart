package cart.controller.auth;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import cart.controller.auth.dto.AuthInfo;
import cart.controller.auth.exception.IllegalAuthenticationException;
import cart.controller.auth.exception.InvalidAuthenticationException;
import cart.dao.UserDao;
import cart.dao.dto.UserDto;

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

        UserDto userDto = userDao.selectBy(authInfo.getEmail())
                .orElseThrow(InvalidAuthenticationException::new);
        validatePasswordMatches(authInfo, userDto);

        return true;
    }

    private void validateHas(AuthInfo authInfo) {
        if (Objects.isNull(authInfo)) {
            throw new IllegalAuthenticationException();
        }
    }

    private void validateHasEmailAndPasswordIn(AuthInfo authInfo) {
        if (!StringUtils.hasLength(authInfo.getEmail()) || !StringUtils.hasLength(authInfo.getPassword())) {
            throw new IllegalAuthenticationException();
        }
    }

    private void validatePasswordMatches(AuthInfo authInfo, UserDto userDto) {
        if (!Objects.equals(authInfo.getPassword(), userDto.getPassword())) {
            throw new InvalidAuthenticationException();
        }
    }
}
