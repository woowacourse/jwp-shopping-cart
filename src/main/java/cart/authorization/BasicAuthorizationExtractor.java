package cart.authorization;

import cart.dto.AuthorizationInformation;
import cart.exception.AuthenticationFailureException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

@Component
public class BasicAuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final String INVALID_MEMBER_MESSAGE = "사용자 정보가 올바르지 않습니다.";

    public AuthorizationInformation extract(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        return extract(header);
    }

    public AuthorizationInformation extract(String header) {
        validateHeader(header);

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decoded = new String(Base64.decodeBase64(authHeaderValue));
        String[] credentials = decoded.split(DELIMITER);
        return new AuthorizationInformation(credentials[0], credentials[1]);
    }

    private void validateHeader(String header) {
        if (notContainsAuthorizationHeader(header) || isNotBasicAuthorization(header)) {
            throw new AuthenticationFailureException(INVALID_MEMBER_MESSAGE);
        }
    }

    private boolean notContainsAuthorizationHeader(String header) {
        return header == null;
    }

    private boolean isNotBasicAuthorization(String header) {
        return !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }

    public AuthorizationInformation extract(NativeWebRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        return extract(header);
    }
}
