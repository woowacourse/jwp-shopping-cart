package cart.auth;

import cart.auth.excpetion.AuthorizeException;
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
            throw new AuthorizeException("인증 정보가 없습니다.");
        }

        return decode(header);
    }

    private static AuthInfo decode(String header) {
        if (header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            final String authorizeHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            final String decodeHeaderValue = new String(Base64.decodeBase64(authorizeHeaderValue));

            return createAuthInfo(decodeHeaderValue);
        }

        throw new AuthorizeException("인증 정보가 다릅니다.");
    }

    private static AuthInfo createAuthInfo(String decodeHeaderValue) {
        final String[] credentials = decodeHeaderValue.split(DELIMITER);
        final String email = credentials[0];
        final String password = credentials[1];

        return new AuthInfo(email, password);
    }

}
