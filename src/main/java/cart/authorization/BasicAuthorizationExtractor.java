package cart.authorization;

import cart.exception.AuthenticationFailureException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthorizationInformation> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final String INVALID_MEMBER_MESSAGE = "사용자 정보가 올바르지 않습니다.";

    @Override
    public AuthorizationInformation extract(HttpServletRequest request) throws AuthenticationFailureException {
        String header = request.getHeader(AUTHORIZATION);

        validateHeader(header);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decoded = new String(Base64.decodeBase64(authHeaderValue));
        String[] credentials = decoded.split(DELIMITER);
        return new AuthorizationInformation(credentials[0], credentials[1]);
    }

    private void validateHeader(String header) throws AuthenticationFailureException {
        if (NotContainsAuthorizationHeader(header) || isNotBasicAuthorization(header)) {
            throw new AuthenticationFailureException(INVALID_MEMBER_MESSAGE);
        }
    }

    private boolean NotContainsAuthorizationHeader(String header) {
        return header == null;
    }

    private boolean isNotBasicAuthorization(String header) {
        return header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
