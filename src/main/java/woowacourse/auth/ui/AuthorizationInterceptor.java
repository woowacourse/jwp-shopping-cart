package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.constant.RequestAttributes;
import woowacourse.auth.domain.token.NullToken;
import woowacourse.auth.domain.token.Token;
import woowacourse.auth.domain.token.ValidToken;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer".toLowerCase();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Token bearerToken = toBearerToken(request.getHeader(AUTHORIZATION));
        request.setAttribute(RequestAttributes.TOKEN, bearerToken);
        return true;
    }

    private Token toBearerToken(String authorizationHeader) {
        if (!isBearerToken(authorizationHeader)) {
            return new NullToken();
        }
        return new ValidToken(toSingleBearerToken(authorizationHeader));
    }

    private boolean isBearerToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(BEARER_TYPE);
    }

    private String toSingleBearerToken(String authorizationHeader) {
        String bearerToken = authorizationHeader.substring(BEARER_TYPE.length()).trim();
        int commaIndex = bearerToken.indexOf(',');
        if (commaIndex > 0) {
            return bearerToken.substring(0, commaIndex);
        }
        return bearerToken;
    }
}
