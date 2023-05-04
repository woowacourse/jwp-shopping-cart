package cart.config;

import cart.controller.dto.AuthInfo;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final int EMAIL_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다.");
        }

        if (isInvalidHeader(header)) {
            throw new IllegalArgumentException("올바르지 않은 헤더입니다.");
        }

        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];

        return new AuthInfo(email, password);
    }

    private static boolean isInvalidHeader(String header) {
        return !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
