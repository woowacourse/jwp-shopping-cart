package woowacourse.auth.support;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {
    public static String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest httpServletRequest) {
        Enumeration<String> headers = httpServletRequest.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                return findAuthHeaderValue(authHeaderValue);
            }
        }
        return null;
    }

    private static String findAuthHeaderValue(String authHeaderValue) {
        int commaIndex = authHeaderValue.indexOf(',');
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }
}
