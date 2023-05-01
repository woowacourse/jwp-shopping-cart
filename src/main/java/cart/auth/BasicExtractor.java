package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import cart.dto.user.UserRequest;

@Component
public class BasicExtractor implements Extractor {

    private final static String AUTHORIZATION = "Authorization";
    private final static String BASIC = "Basic";
    private final static String DELIMITER = ":";
    public static final String NO_BASIC_MESSAGE = "회원이 아닙니다.";

    @Override
    public UserRequest extractUser(HttpHeaders header) {
        final String basic = header.getFirst(AUTHORIZATION);
        validate(basic);

        String authHeaderValue = basic.substring(BASIC.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        return new UserRequest(email, password);
    }

    private void validate(String basic) {
        if (basic == null || !basic.startsWith(BASIC)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, NO_BASIC_MESSAGE);
        }
    }
}
