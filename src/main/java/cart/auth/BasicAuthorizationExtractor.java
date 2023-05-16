package cart.auth;

import cart.auth.interceptor.AuthorizationException;
import cart.controller.dto.auth.AuthInfoDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BasicAuthorizationExtractor {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfoDto extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new AuthorizationException("로그인 정보가 존재하지 않습니다.");
        }

        if ((header.toLowerCase()
                   .startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length())
                                           .trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return new AuthInfoDto(email, password);
        }

        return null;
    }
}
