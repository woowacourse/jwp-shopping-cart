package cart.auth.extractor;

import cart.auth.dto.BasicAuthInfo;
import cart.exception.auth.UnauthenticatedException;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public static BasicAuthInfo extract(final String header) {
        if (header == null || !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new UnauthenticatedException();
        }
        String[] credentials = extractCredentials(header);
        if (credentials.length != 2) {
            throw new UnauthenticatedException();
        }
        return new BasicAuthInfo(credentials[0], credentials[1]);
    }

    private static String[] extractCredentials(final String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decodedString = new String(Base64.decodeBase64(authHeaderValue));
        return decodedString.split(DELIMITER);
    }
}
