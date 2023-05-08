package cart.controller.authentication;

import cart.exception.AuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class BasicAuthorizationExtractor {

    private static final String DELIMITER = ":";

    public static AuthInfo extract(final String header) {
        if (header == null || header.isBlank()) {
            throw new AuthorizationException("header에 회원 정보가 입력되지 않았습니다.");
        }

        if (header.toLowerCase().startsWith(HttpServletRequest.BASIC_AUTH.toLowerCase())) {
            final String decodedString = decodeHeaderValue(header);

            final List<String> credentials = Arrays.asList(decodedString.split(DELIMITER, 2));
            final String email = credentials.get(0);
            final String password = credentials.get(1);
            return new AuthInfo(email, password);
        }
        throw new AuthorizationException("지원하지 않는 인증 방식입니다. Basic 인증 방식을 사용해주세요.");
    }

    private static String decodeHeaderValue(final String header) {
        final String authHeaderValue = header.substring(HttpServletRequest.BASIC_AUTH.length()).trim();
        final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        final String decodedString = new String(decodedBytes);

        if (!decodedString.contains(DELIMITER)) {
            throw new AuthorizationException("회원 정보 입력 형식이 잘못되었습니다. \"email:password\"로 입력해주세요.");
        }

        return decodedString;
    }
}
