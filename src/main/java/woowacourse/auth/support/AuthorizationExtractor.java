package woowacourse.auth.support;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    public static String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = extractHeaders(request);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (isBearerToken(value)) {
                String token = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
                token = parseToken(token);
                return token;
            }
        }
        return null;
    }

    private static Enumeration<String> extractHeaders(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        return headers;
    }

    private static boolean isBearerToken(String value) {
        return value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }

    private static String parseToken(String token) {
        int commaIndex = token.indexOf(',');
        if (commaIndex > 0) {
            token = token.substring(0, commaIndex);
        }
        return token;
    }
}
