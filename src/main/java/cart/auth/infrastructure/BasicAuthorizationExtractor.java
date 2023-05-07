package cart.auth.infrastructure;

import cart.dto.UserAuthInfo;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<UserAuthInfo> {
    private static final String BASIC_TYPE = "basic";
    private static final String DELIMITER = ":";

    @Override
    public UserAuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE))) {
            String[] credentials = getCredentials(header);

            String email = credentials[0];
            String password = credentials[1];

            return new UserAuthInfo(email, password);
        }

        return null;
    }

    private String[] getCredentials(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);
        return decodedString.split(DELIMITER);
    }
}
