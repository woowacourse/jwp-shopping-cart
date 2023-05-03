package cart.authorization;

import cart.exception.AuthenticationFailureException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.parser.Authorization;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInformation> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public AuthInformation extract(HttpServletRequest request) throws AuthenticationFailureException {
        String header = request.getHeader(AUTHORIZATION);

        if (NotContainsAuthorizationHeader(header) || isNotBasicAuthorization(header)) {
            throw new AuthenticationFailureException("사용자 정보가 올바르지 않습니다.");
        }
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decoded = new String(Base64.decodeBase64(authHeaderValue));
        String[] credentials = decoded.split(DELIMITER);
        return new AuthInformation(credentials[0], credentials[1]);
}

    private static boolean NotContainsAuthorizationHeader(String header) {
        return header == null;
    }

    private static boolean isNotBasicAuthorization(String header) {
        return header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
