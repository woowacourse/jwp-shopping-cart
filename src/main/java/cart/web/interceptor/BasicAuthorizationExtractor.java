package cart.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import cart.web.cart.dto.AuthInfo;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthInfo extract(final HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        checkAuthHeaderIsNotNull(authHeader);
        checkDecodedType(authHeader);
        String[] credentials = extractValidSingleKeyValuePair(authHeader);

        final String email = credentials[0];
        final String password = credentials[1];
        return new AuthInfo(email, password);
    }

    private void checkAuthHeaderIsNotNull(final String authHeader) {
        if (authHeader == null) {
            throw new AuthorizationException("올바른 인증 정보가 필요합니다.");
        }
    }

    private void checkDecodedType(final String authHeader) {
        if (!authHeader.startsWith(BASIC_TYPE)) {
            throw new AuthorizationException("올바른 인증 정보가 필요합니다.");
        }
    }

    private String[] extractValidSingleKeyValuePair(final String authHeader) {
        final String authHeaderValue = authHeader.substring(BASIC_TYPE.length()).trim();
        final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        final String decodedString = new String(decodedBytes);

        final String[] credentials = decodedString.split(DELIMITER);
        if (credentials.length != 2) {
            throw new AuthorizationException("올바른 인증 정보가 필요합니다.");
        }
        return credentials;
    }
}
