package cart.auth;

import cart.exception.UserAuthorizationException;
import cart.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

import static cart.auth.AuthArgumentResolver.AUTHORIZED_EMAIL_KEY;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String BASIC_AUTH_PREFIX = "Basic";
    private static final String BASIC_AUTH_HEADER_REGEX = "^" + BASIC_AUTH_PREFIX + " .+$";
    private static final String DELIMITER = ":";

    private final UserService userService;

    public LoginInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authHeaderValue = request.getHeader(AUTH_HEADER_KEY);

        if (authHeaderValue == null || !authHeaderValue.matches(BASIC_AUTH_HEADER_REGEX)) {
            throw new UserAuthorizationException("Basic 인증 형식에 맞지 않습니다.");
        }

        final String authCredential = authHeaderValue.substring(BASIC_AUTH_PREFIX.length()).trim();
        final String decodedCredential = new String(Base64.getDecoder().decode(authCredential));

        final String[] emailPassword = decodedCredential.split(DELIMITER);
        final String email = emailPassword[0];
        final String password = emailPassword[1];

        userService.validateUser(email, password);

        request.setAttribute(AUTHORIZED_EMAIL_KEY, email);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
