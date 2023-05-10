package cart.global.annotation;

import cart.auth.AuthAccount;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthorizationArgumentResolver extends LogInAuthorizationArgumentResolver {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIM = ":";

    @Override
    protected boolean canDecoding(final String header) {
        return header.toLowerCase().startsWith((BASIC_TYPE.toLowerCase()));
    }

    @Override
    protected AuthAccount decode(final String header) {
        final String[] credentials = decodingByBase64(header).split(DELIM);

        final String email = credentials[0];
        final String password = credentials[1];

        return new AuthAccount(email, password);
    }

    private String decodingByBase64(final String header) {
        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        final byte[] decodedBytes = Base64.getDecoder().decode(authHeaderValue);

        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
