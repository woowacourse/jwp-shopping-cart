package cart.auth.infrastructure;

import cart.domain.cart.AuthInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.context.request.NativeWebRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthInfo extract(NativeWebRequest webRequest) {
        String header = webRequest.getHeader(AUTHORIZATION);

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
