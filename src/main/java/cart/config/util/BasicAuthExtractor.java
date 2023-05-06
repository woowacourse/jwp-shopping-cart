package cart.config.util;

import cart.exception.AuthPrincipalInValidException;
import cart.exception.AuthorizationException;
import cart.exception.InvalidCredentialLength;
import cart.service.dto.MemberInfo;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTH_TYPE = HttpServletRequest.BASIC_AUTH;
    private static final String DELIMITER = ":";

    public MemberInfo extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        validateAuthHeader(header);

        final String authHeaderValue = header.substring(AUTH_TYPE.length()).trim();
        final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        final String decodedString = new String(decodedBytes);

        final String[] credentials = decodedString.split(DELIMITER);
        validateCredentialLength(credentials);
        final String email = credentials[0];
        final String password = credentials[1];

        return new MemberInfo(email, password);
    }

    private static void validateAuthHeader(final String header) {
        headerRequireNonNull(header);
        validAuthType(header);
    }

    private static void validAuthType(final String header) {
        if (!header.toLowerCase().startsWith(AUTH_TYPE.toLowerCase())) {
            throw new AuthPrincipalInValidException();
        }
    }

    private static void headerRequireNonNull(final String header) {
        if (header == null) {
            throw new AuthorizationException();
        }
    }

    private static void validateCredentialLength(final String[] credentials) {
        if (credentials.length != 2) {
            throw new InvalidCredentialLength();
        }
    }
}
