package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.constant.RequestAttributes;
import woowacourse.auth.domain.Token;
import woowacourse.common.exception.ForbiddenException;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer".toLowerCase();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        validateTokenExistence(authorizationHeader);
        String bearerToken = toSingleBearerToken(authorizationHeader.substring(BEARER_TYPE.length()).trim());
        request.setAttribute(RequestAttributes.TOKEN, new Token(bearerToken));
        return true;
    }

    private void validateTokenExistence(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(BEARER_TYPE)) {
            throw new ForbiddenException();
        }
    }

    private String toSingleBearerToken(String bearerToken) {
        int commaIndex = bearerToken.indexOf(',');
        if (commaIndex > 0) {
            return bearerToken.substring(0, commaIndex);
        }
        return bearerToken;
    }
}
