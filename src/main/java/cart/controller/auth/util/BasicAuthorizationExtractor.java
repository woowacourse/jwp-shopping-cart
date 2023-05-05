package cart.controller.auth.util;

import cart.controller.auth.dto.LoginUser;
import cart.exception.UnauthorizedException;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    public static LoginUser extract(final String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new UnauthorizedException("인증 정보가 없습니다.");
        }
        if (!authorizationHeader.toLowerCase().startsWith("basic")) {
            throw new UnauthorizedException("잘못된 인증입니다.");
        }
        String credentials = authorizationHeader.split("\\s")[1];
        byte[] bytes = Base64.decodeBase64(credentials);
        String[] emailAndPassword = new String(bytes).split(":");
        String email = emailAndPassword[0];
        String password = emailAndPassword[1];
        return new LoginUser(email, password);
    }
}
