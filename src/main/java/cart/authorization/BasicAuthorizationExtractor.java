package cart.authorization;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor<MemberAuthorizationInfo> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    public MemberAuthorizationInfo extract(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null) {
            return null;
        }

        if (isAuthorizationHeaderBasic(authorizationHeader)) {
            String decodedString = decodeAuthorizationHeader(authorizationHeader);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[EMAIL_INDEX];
            String password = credentials[PASSWORD_INDEX];

            return new MemberAuthorizationInfo(email, password);
        }

        return null;
    }

    private boolean isAuthorizationHeaderBasic(String header) {
        return header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }

    private String decodeAuthorizationHeader(String authorizationHeader) {
        String authHeaderValue = authorizationHeader.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        return new String(decodedBytes);
    }
}
