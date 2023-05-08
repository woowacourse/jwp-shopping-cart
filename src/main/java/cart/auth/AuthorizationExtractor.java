package cart.auth;

import cart.domain.member.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class AuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public MemberDto extract(final String header) {
        if (header == null) {
            return null;
        }
        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            final byte[] decodedBytes = Base64Utils.decode(authHeaderValue.getBytes());
            final String decodedString = new String(decodedBytes);
            final String[] credentials = decodedString.split(DELIMITER);
            final String email = credentials[0];
            final String password = credentials[1];
            return new MemberDto(email, password);
        }
        return null;
    }
}