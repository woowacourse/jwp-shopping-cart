package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import woowacourse.common.exception.ForbiddenException;

public class HttpHeaderUtil {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_TYPE = "Bearer".toLowerCase();

    private HttpHeaderUtil() {
    }

    public static String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        validateAuthorizationHeader(authorizationHeader);
        return extractBearerToken(authorizationHeader);
    }

    private static void validateAuthorizationHeader(String header) {
        if (header == null || !header.toLowerCase().startsWith(BEARER_TYPE)) {
            throw new ForbiddenException();
        }
    }

    private static String extractBearerToken(String authorizationHeader) {
        String bearerToken = authorizationHeader.substring(BEARER_TYPE.length()).trim();
        int commaIndex = bearerToken.indexOf(',');
        if (commaIndex > 0) {
            bearerToken = bearerToken.substring(0, commaIndex);
        }
        return bearerToken;
    }
}
