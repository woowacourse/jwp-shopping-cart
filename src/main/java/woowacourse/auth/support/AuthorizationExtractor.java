package woowacourse.auth.support;

import java.util.Enumeration;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import woowacourse.exception.UnauthorizedException;

public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public static String extract(NativeWebRequest request) {
        final String header = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(header)) {
            throw new UnauthorizedException();
        }

        return header.replace(BEARER_TYPE, "").trim();
    }

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
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

        return null;
    }
}
