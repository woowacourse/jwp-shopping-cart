package woowacourse.auth.ui.config;

import static org.springframework.web.cors.CorsUtils.isPreFlightRequest;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider tokenProvider;

    public LoginInterceptor(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreFlightRequest(request) || isSignUpRequest(request)) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        if (isAuthorizedRequest(token)) {
            return true;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    private boolean isSignUpRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/customers")
                && request.getMethod().equalsIgnoreCase("post");
    }

    private boolean isAuthorizedRequest(String token) {
        return tokenProvider.validateToken(token) && !Objects.isNull(token);
    }
}
