package cart.config.auth;

import org.springframework.util.Base64Utils;

public class AuthInfo {
    private static final String AUTHORIZATION_BASIC = "Basic ";
    private static final int EMAIL = 0;
    private static final int PASSWORD = 1;

    private final String email;
    private final String password;

    private AuthInfo(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static AuthInfo from(final String basicToken) {
        final String decodedToken = parseBasicToken(basicToken);
        final String[] emailAndPassword = decodedToken.split(":");

        return new AuthInfo(emailAndPassword[EMAIL], emailAndPassword[PASSWORD]);
    }

    private static String parseBasicToken(final String basicToken) {
        final String token = basicToken.replaceFirst(AUTHORIZATION_BASIC, "");
        return decode(token);
    }

    private static String decode(final String token) {
        return new String(Base64Utils.decodeFromString(token));
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
