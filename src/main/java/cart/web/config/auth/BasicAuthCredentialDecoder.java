package cart.web.config.auth;

import cart.web.controller.dto.request.AuthorizedUserRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthCredentialDecoder implements AuthCredentialDecoder<AuthorizedUserRequest> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthorizedUserRequest decodeCredential(String value) {
        if ((value.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = value.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return new AuthorizedUserRequest(email, password);
        }

        throw new IllegalArgumentException(); //TODO: 모호한 exception
    }
}
