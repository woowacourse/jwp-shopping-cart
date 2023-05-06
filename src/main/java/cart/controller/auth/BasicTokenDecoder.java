package cart.controller.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import cart.service.dto.UserAuthDto;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicTokenDecoder {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public static UserAuthDto extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            throw new UnauthorizedException(AUTHORIZATION + " 헤더가 존재하지 않습니다.");
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return new UserAuthDto(email, password);
        }

        throw new UnauthorizedException(AUTHORIZATION + " 헤더가 " + BASIC_TYPE + "으로 시작하지 않습니다.");
    }
}
