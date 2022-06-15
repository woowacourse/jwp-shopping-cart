package woowacourse.auth.support;

import org.springframework.http.HttpHeaders;
import woowacourse.auth.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {
    public static String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isBearerToken(token)) {
            return token.substring(BEARER_TYPE.length()).trim();
        }
        throw new UnauthorizedException();
    }

    private static boolean isBearerToken(String bearerToken) {
        return bearerToken != null
                && bearerToken.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }
}
