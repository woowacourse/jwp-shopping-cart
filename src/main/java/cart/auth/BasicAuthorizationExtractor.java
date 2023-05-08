package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthUserDetail> {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    public static final int AUTH_INFO_SIZE = 2;

    @Override
    public AuthUserDetail extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String[] credentials = extractCredentials(header);

            return getAuthInfo(credentials);
        }
        return null;
    }

    private AuthUserDetail getAuthInfo(String[] credentials) {
        if (credentials.length < AUTH_INFO_SIZE) {
            return null;
        }
        String email = credentials[0];
        String password = credentials[1];
        return new AuthUserDetail(email, password);
    }

    private String[] extractCredentials(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);
        return decodedString.split(DELIMITER);
    }
}

