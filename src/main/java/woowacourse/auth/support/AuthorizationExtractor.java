package woowacourse.auth.support;

import woowacourse.shoppingcart.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    private static final int HEADER_VALUE_BEGIN_INDEX = 0;
    private static final int NOT_EXIST_COMMA_SIZE = 0;

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(HEADER_VALUE_BEGIN_INDEX, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > NOT_EXIST_COMMA_SIZE) {
                    authHeaderValue = authHeaderValue.substring(HEADER_VALUE_BEGIN_INDEX, commaIndex);
                }
                return authHeaderValue;
            }
        }

        throw new UnauthorizedException();
    }
}
