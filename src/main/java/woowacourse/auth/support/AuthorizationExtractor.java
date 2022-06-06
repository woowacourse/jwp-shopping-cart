package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import woowacourse.auth.exception.InvalidTokenException;

public class AuthorizationExtractor {

    public static String BEARER_TYPE = "Bearer ";

    public static String extract(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateHeader(header);
        return header.substring(BEARER_TYPE.length());
    }

    private static void validateHeader(String header) {
        if (header == null) {
            throw new InvalidTokenException();
        }
        if (!header.startsWith(BEARER_TYPE)) {
            throw new InvalidTokenException();
        }
    }
}
