package cart.auth;

import cart.exception.BasicAuthException;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BasicAuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic ";
    private static final String DELIMITER = ":";

    public List<String> extract(final HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        validateAuthorizationHeader(header);
        return parseAuthorizationHeader(header);
    }

    private void validateAuthorizationHeader(final String header) {
        if (header == null) {
            throw new BasicAuthException("Header에 사용자 인증 정보가 존재하지 않습니다.");
        }

        if (!header.startsWith(BASIC_TYPE)) {
            throw new BasicAuthException("Basic 타입의 Header가 아닙니다.");
        }
    }

    private List<String> parseAuthorizationHeader(final String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        if (!decodedString.contains(DELIMITER)) {
            throw new BasicAuthException("유효한 Basic Token 값이 아닙니다.");
        }
        return List.of(decodedString.split(DELIMITER));
    }
}
