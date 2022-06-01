package woowacourse.auth.ui;

import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.shoppingcart.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String BEARER = "BEARER";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String auth = request.getHeader("Authorization");
        if (auth == null) {
            throw new UnauthorizedException();
        }
        final String token = Objects.requireNonNull(auth).substring(BEARER.length() + 1);
        request.setAttribute("token", token);
        return true;
    }
}
