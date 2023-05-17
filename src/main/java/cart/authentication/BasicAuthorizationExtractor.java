package cart.authentication;

import cart.dto.AuthInfo;
import cart.exception.AuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            throw new AuthorizationException("Authorization 헤더가 존재하지 않습니다");
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            return makeAuthInfo(header);
        }

        throw new AuthorizationException("Basic 타입 인증이 아닙니다.");
    }

    private AuthInfo makeAuthInfo(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        if (decodedString.contains(DELIMITER)) {
            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];
            return new AuthInfo(email, password);
        }
        throw new AuthorizationException("인증 정보가 Delimiter를 포함하지 않습니다.");
    }

}
