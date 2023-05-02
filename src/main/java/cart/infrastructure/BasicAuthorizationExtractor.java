package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;

import cart.dto.UserResponse;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public UserResponse extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if (header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            final String decodedString = new String(decodedBytes);

            final String[] credentials = decodedString.split(DELIMITER);
            final String email = credentials[0];
            final String password = credentials[1];

            return new UserResponse(email, password);
        }

        return null;
    }
}
