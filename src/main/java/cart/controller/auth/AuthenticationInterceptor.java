package cart.controller.auth;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

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
        Credential credential = BasicAuthorizationExtractor.extract(request);
        validateHas(credential);
        validateHasEmailAndPasswordIn(credential);

        UserDto userDto = userDao.selectBy(credential.getEmail())
                .orElseThrow(InvalidAuthenticationException::new);
        validatePasswordMatches(credential, userDto);

        return true;
    }

    private void validateHas(Credential credential) {
        if (Objects.isNull(credential)) {
            throw new IllegalAuthenticationException();
        }
    }

    private void validateHasEmailAndPasswordIn(Credential credential) {
        if (!StringUtils.hasLength(credential.getEmail()) || !StringUtils.hasLength(credential.getPassword())) {
            throw new IllegalAuthenticationException();
        }
    }

    private void validatePasswordMatches(Credential credential, UserDto userDto) {
        if (!Objects.equals(credential.getPassword(), userDto.getPassword())) {
            throw new InvalidAuthenticationException();
        }
    }
}
