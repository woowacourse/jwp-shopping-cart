package cart.config.auth;

import cart.common.auth.AuthInfo;
import org.springframework.util.Base64Utils;

public class BasicAuthProvider {
    private static final String AUTHORIZATION_BASIC = "Basic ";
    private static final int EMAIL = 0;
    private static final int PASSWORD = 1;

    public AuthInfo getAuthInfo(final String basicToken) {
        final String decodedToken = parseBasicToken(basicToken);

        final String[] emailAndPassword = decodedToken.split(":");
        final String email = emailAndPassword[EMAIL];
        final String password = emailAndPassword[PASSWORD];

        return new AuthInfo(email, password);

    }

    private String parseBasicToken(final String basicToken) {
        final String token = basicToken.replaceFirst(AUTHORIZATION_BASIC, "");
        return decode(token);
    }

    private String decode(final String token) {
        return new String(Base64Utils.decodeFromString(token));
    }
}
