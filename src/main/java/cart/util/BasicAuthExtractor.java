package cart.util;

import cart.controller.dto.request.LoginRequest;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthExtractor {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final String AUTHORIZATION = "Authorization";

    public LoginRequest extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

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

            return new LoginRequest(email, password);
        }

        return null;
    }
}
