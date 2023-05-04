package cart.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.controller.exception.AuthException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor {

    private static final String BASIC_REGEX = "^Basic [A-Za-z0-9+/]+=*$";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int AUTHENTICATION_INFO_SIZE = 2;

    @Override
    public AuthDto extract(final HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION);
        validate(authorization);
        final String token = authorization.replace(BASIC_TYPE, "").trim();

        return decode(token);
    }

    private void validate(final String authorization) {
        if (authorization == null) {
            throw new AuthException("사용자 인증이 필요합니다.");
        }
        if (authorization.matches(BASIC_REGEX)) {
            throw new AuthException("유효하지 않은 인증 형식입니다.");
        }
    }

    private AuthDto decode(final String token) {
        final String decodedToken = new String(Base64.decodeBase64(token));

        final List<String> member = Arrays.stream(decodedToken.split(DELIMITER))
                .collect(Collectors.toUnmodifiableList());
        if (member.size() != AUTHENTICATION_INFO_SIZE) {
            throw new AuthException("유효하지 않은 인증 정보입니다.");
        }

        final String email = member.get(0);
        final String password = member.get(1);

        return new AuthDto(email, password);
    }
}
