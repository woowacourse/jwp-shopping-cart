package woowacourse.auth.support;

import woowacourse.shoppingcart.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TYPE = "Bearer";

    public static String extract(final HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String authHeaderValue = extractHeaderValue(headers.nextElement());
            if (authHeaderValue != null) {
                return authHeaderValue;
            }
        }

        throw new UnauthorizedException();
    }

    private static String extractHeaderValue(String value) {
        if (!isBearer(value)) {
            return null;
        }

        return value.substring(BEARER_TYPE.length()).trim();
    }

    private static boolean isBearer(String value) {
        return value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }
}
