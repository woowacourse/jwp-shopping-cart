package woowacourse.auth.ui;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.AuthorizationException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = AuthorizationExtractor.extract(request);
        validateToken(token);

        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("payload", jwtTokenProvider.getPayload(token));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new AuthorizationException("토큰이 존재하지 않습니다.");
        }
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("인증되지 않은 회원입니다.");
        }
    }
}
