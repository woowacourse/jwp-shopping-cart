package woowacourse.auth.support;

import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    public static String BEARER_TYPE = "Bearer";

    /**
     * request header의 authorization에 존재하는 토큰을 가져온다. token의 형식은 "Bearer xxx.yyy.zzz"이다.
     *
     * @return header에 존재하는 accessToken(xxx.yyy.zzz) 값
     */
    public static Optional<String> extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                return Optional.of(getAuthHeaderValue(authHeaderValue, commaIndex));
            }
        }
        return Optional.empty();
    }

    private static String getAuthHeaderValue(String authHeaderValue, int commaIndex) {
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }
}
