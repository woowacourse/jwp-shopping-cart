package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class AuthenticateService {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public AuthInfo extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);

        if (ObjectUtils.isEmpty(header)) {
            return AuthInfo.nonAuthorize();
        }

        return decode(header);
    }

    private static AuthInfo decode(String header) {
        if (header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            final String authorizeHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            final String decodeHeaderValue = new String(Base64.decodeBase64(authorizeHeaderValue));

            return createAuthInfo(decodeHeaderValue);
        }

        return AuthInfo.nonAuthorize();
    }

    private static AuthInfo createAuthInfo(String decodeHeaderValue) {
        final String[] credentials = decodeHeaderValue.split(DELIMITER);
        final String email = credentials[0];
        final String password = credentials[1];

        return new AuthInfo(email, password);
    }

}
