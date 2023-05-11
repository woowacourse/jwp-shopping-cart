package cart.web.auth;

import cart.exception.AuthorizationException;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor {
    private static final BasicAuthorizationExtractor EXTRACTOR = new BasicAuthorizationExtractor();
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "basic";

    private BasicAuthorizationExtractor() {
    }

    public static BasicAuthorizationExtractor getInstance() {
        return EXTRACTOR;
    }

    public String extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        validateHeader(header);

        return decodeAuthorizationHeader(header);
    }

    private void validateHeader(String header) {
        if (header == null) {
            throw new AuthorizationException();
        }

        if (!header.toLowerCase().startsWith(BASIC_TYPE)) {
            throw new AuthorizationException();
        }
    }

    private String decodeAuthorizationHeader(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64Utils.decodeFromUrlSafeString(authHeaderValue);

        return new String(decodedBytes);
    }
}
