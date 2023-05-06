package cart.web.config.auth;

import cart.web.config.auth.exception.IllegalCredentialException;
import cart.web.dto.request.AuthorizedUserRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthCredentialDecoder implements AuthCredentialDecoder<AuthorizedUserRequest> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthorizedUserRequest decodeCredential(final String value) {
        if ((value.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            final String authHeaderValue = value.substring(BASIC_TYPE.length()).trim();
            final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            final String decodedString = new String(decodedBytes);

            final String[] credentials = decodedString.split(DELIMITER);
            final String email = credentials[0];
            final String password = credentials[1];

            return new AuthorizedUserRequest(email, password);
        }

        throw new IllegalCredentialException();
    }
}
