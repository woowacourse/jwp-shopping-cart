package woowacourse.shoppingcart.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.exception.unauthorized.UnauthorizedTokenException;
import woowacourse.shoppingcart.support.AuthorizationExtractor;
import woowacourse.shoppingcart.support.JwtTokenProvider;

public class LoginInterceptor implements HandlerInterceptor {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(final CustomerService customerService, final JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (isPreflight(request)) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        if (token == null) {
            throw new UnauthorizedTokenException();
        }
        validateToken(token);
        checkExistCustomer(token);

        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private void validateToken(final String token) {
        final boolean isValidToken = jwtTokenProvider.validateToken(token);
        if (isValidToken) {
            return;
        }
        throw new UnauthorizedTokenException();
    }

    private void checkExistCustomer(String token) {
        final String email = jwtTokenProvider.getPayload(token);
        final boolean existEmail = customerService.isExistEmail(email);
        if (existEmail) {
            return;
        }
        throw new UnauthorizedTokenException();
    }
}
