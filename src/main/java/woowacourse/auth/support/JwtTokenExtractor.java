package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.exception.unauthorized.UnauthorizedException;

@Component
public class JwtTokenExtractor {

    public static String BEARER_TYPE = "Bearer";

    public String extract(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isBearerToken(token)) {
            return token.substring(BEARER_TYPE.length()).trim();
        }
        throw new UnauthorizedException();
    }

    private boolean isBearerToken(String bearerToken) {
        return bearerToken != null
                && bearerToken.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }
}
