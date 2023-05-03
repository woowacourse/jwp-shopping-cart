package cart.controller.util;

import cart.exception.AuthorizationException;
import cart.service.dto.MemberInfo;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthExtractor {

    private static final String BASIC_AUTH_PREFIX = "Basic";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String DELIMITER = ":";

    public static final MemberInfo extract(final HttpServletRequest httpServletRequest) {
        final String credential = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        if (credential == null) {
            throw new AuthorizationException();
        }

        if (!credential.startsWith(BASIC_AUTH_PREFIX)) {
            throw new RuntimeException();
        }

        String authHeaderValue = credential.substring(BASIC_AUTH_PREFIX.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        return new MemberInfo(email, password);
    }
}
