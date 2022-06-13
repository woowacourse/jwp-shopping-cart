package woowacourse.auth.support;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {
    public static String BEARER_TYPE = "Bearer";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (isBearerToken(value)) {
                setAccessTokenType(request, value);
                return getOnlyAuthHeaderValue(getBearerToken(value));
            }
        }

        return null;
    }

    private static boolean isBearerToken(String value) {
        return value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }

    private static void setAccessTokenType(HttpServletRequest request, String value) {
        request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
    }

    private static String getBearerToken(String value) {
        return value.substring(BEARER_TYPE.length()).trim();
    }

    private static String getOnlyAuthHeaderValue(String authHeaderValue) {
        int commaIndex = authHeaderValue.indexOf(',');
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }
}
