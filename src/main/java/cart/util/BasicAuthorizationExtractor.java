package cart.util;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.dto.AuthDto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthorizationExtractor implements AuthorizationExtractor {

    private static final String BASIC_TYPE = "Basic";
    private static final String EMPTY = "";
    private static final String DELIMITER = ":";

    @Override
    public AuthDto extract(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        final String token = header.replace(BASIC_TYPE, EMPTY).trim();

        final AuthDto authDto = decode(token);
        return authDto;
    }

    private AuthDto decode(final String token) {
        final String decodedToken = new String(Base64.decodeBase64(token));

        final List<String> member = Arrays.stream(decodedToken.split(DELIMITER))
                .collect(Collectors.toUnmodifiableList());

        final String email = member.get(0);
        final String password = member.get(1);

        return new AuthDto(email, password);
    }
}
