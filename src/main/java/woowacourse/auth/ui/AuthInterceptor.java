package woowacourse.auth.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.AuthException;
import woowacourse.auth.support.AuthenticationContext;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.customer.Email;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider, AuthenticationContext authenticationContext) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        String accessToken = AuthorizationExtractor.extract(Objects.requireNonNull(request))
                .orElseThrow(() -> new AuthException("토큰 값이 잘못되었습니다."));
        String subject = jwtTokenProvider.getSubject(accessToken);
        authenticationContext.setEmail(new Email(subject));
        return true;
    }
}
