package cart.authentication;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if (header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            return getAuthInfoInHeader(header);
        }

        return null;
    }

    private AuthInfo getAuthInfoInHeader(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedHeaderValue = new String(decodedBytes);
        String[] credentials = decodedHeaderValue.split(DELIMITER);
        return new AuthInfo(credentials[0], credentials[1]);
    }
}
