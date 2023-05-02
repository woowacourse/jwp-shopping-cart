package cart.infra;

import cart.dto.LoginRequestDto;
import cart.exception.AuthorizationException;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<LoginRequestDto> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public LoginRequestDto extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        if (header.startsWith(BASIC_TYPE)) {
            final String[] credentials = decode(header).split(DELIMITER);
            final String email = credentials[0];
            final String password = credentials[1];
            return new LoginRequestDto(email, password);
        }
        throw new AuthorizationException("Basic Authentication이 아닙니다.");
    }

    private String decode(final String header) {
        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        final byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        return new String(decodedBytes);
    }
}
