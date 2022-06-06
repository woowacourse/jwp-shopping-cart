package woowacourse.auth.support;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import woowacourse.shoppingcart.exception.UnauthorizedException;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    private static final int HEADER_VALUE_BEGIN_INDEX = 0;

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE,
                        value.substring(HEADER_VALUE_BEGIN_INDEX, BEARER_TYPE.length()).trim());
                return authHeaderValue;
            }
        }

        throw new UnauthorizedException();
    }
}
