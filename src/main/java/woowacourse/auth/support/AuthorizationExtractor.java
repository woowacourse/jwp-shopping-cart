package woowacourse.auth.support;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import woowacourse.auth.exception.AuthorizationException;

public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    /*
        Input : Authorization: Bearer ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f
        Output : ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f
     */
    public static String extract(final HttpServletRequest request) {
        final Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            final String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        throw new AuthorizationException("토큰을 추출할 수 없습니다😤");
    }
}
