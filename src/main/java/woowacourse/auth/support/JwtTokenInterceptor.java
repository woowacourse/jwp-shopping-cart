package woowacourse.auth.support;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String token = AuthorizationExtractor.extract(request);
        validateToken(token);
        return true;
    }

    private void validateToken(final String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

}
