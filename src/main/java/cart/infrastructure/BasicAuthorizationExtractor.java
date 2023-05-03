package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;

import cart.controller.dto.AuthInfo;

public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfo extract(final HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return new AuthInfo(email, password);
        }

        return null;
    }
}
