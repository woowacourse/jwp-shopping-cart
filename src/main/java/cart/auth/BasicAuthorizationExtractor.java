package cart.auth;

import cart.domain.member.Member;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<Member> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public Member extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            final byte[] decodeBytes = Base64.decodeBase64(authHeaderValue);
            final String decodedString = new String(decodeBytes);

            final String[] credentials = decodedString.split(DELIMITER);
            final String username = credentials[0];
            final String password = credentials[1];

            return new Member(username, password);
        }

        return null;
    }
}
