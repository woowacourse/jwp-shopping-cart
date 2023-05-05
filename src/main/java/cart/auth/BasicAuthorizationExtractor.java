package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public Credential extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        System.out.println("header " + header);
        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            final String decodedString = new String(decodedBytes);

            final String[] credentials = decodedString.split(DELIMITER);
            final String email = credentials[0];
            final String password = credentials[1];

            System.out.println("header email : " + email);
            System.out.println("header password " + password);
            return new Credential(email, password);
        }

        return null;
    }
}
