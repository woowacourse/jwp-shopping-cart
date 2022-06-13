package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.TokenProvider;
import woowacourse.auth.exception.NoAuthorizationHeaderException;

public class LoginInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    public LoginInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new NoAuthorizationHeaderException();
        }

        String accessToken = AuthorizationExtractor.extract(authorizationHeader);
        if (!tokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException();
        }

        return true;
    }
}
