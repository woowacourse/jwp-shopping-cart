package cart.auth;

import cart.dto.auth.AuthInfo;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {
    private static final String TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthInfo extract(final HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            return null;
        }

        if (header.toLowerCase().startsWith(TYPE.toLowerCase())) {
            String value = header.substring(TYPE.length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            return AuthInfo.builder()
                    .email(credentials[0])
                    .password(credentials[1])
                    .build();
        }

        return null;
    }
}
