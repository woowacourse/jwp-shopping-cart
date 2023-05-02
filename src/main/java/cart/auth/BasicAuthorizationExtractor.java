package cart.auth;

import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    public AuthInfo extract(HttpServletRequest request) {
        final String header = request.getHeader("authorization");

        if (header == null) {
            return null;
        }

        if (header.toLowerCase().startsWith("Basic".toLowerCase())) {
            final String authHeaderValue = header.substring("Basic".length()).trim();
            final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            final String decodedString = new String(decodedBytes);

            final String[] credentials = decodedString.split(":");
            final String email = credentials[0];
            final String password = credentials[1];

            return new AuthInfo(email, password);
        }

        return null;
    }

}
