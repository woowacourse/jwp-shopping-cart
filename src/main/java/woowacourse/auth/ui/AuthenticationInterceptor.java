package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.RequestTokenContext;
import woowacourse.shoppingcart.exception.InvalidTokenException;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final RequestTokenContext requestTokenContext;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(RequestTokenContext requestTokenContext, JwtTokenProvider jwtTokenProvider) {
        this.requestTokenContext = requestTokenContext;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        final String token = AuthorizationExtractor.extract(request);
        jwtTokenProvider.validateToken(token);
        final String payload = jwtTokenProvider.getPayload(token);
        validatePayload(payload);
        requestTokenContext.setCustomerId(payload);
        return true;
    }

    private void validatePayload(String payload) {
        if (payload == null) {
            throw new InvalidTokenException();
        }
    }
}
