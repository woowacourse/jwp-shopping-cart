package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    String AUTHORIZATION = "Authorization";

    @Override
    public AuthInfo extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new IllegalArgumentException("Authorization 헤더가 존재하지 않습니다.");
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String decodedString = getDecodedString(header);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return new AuthInfo(email, password);
        }

        throw new IllegalArgumentException("Basic 방식의 인증이 아닙니다.");
    }

    private static String getDecodedString(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
