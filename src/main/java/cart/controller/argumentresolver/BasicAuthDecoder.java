package cart.controller.argumentresolver;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import cart.dto.user.UserRequest;

public class BasicAuthDecoder implements AuthDecoder {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC_TYPE = "Basic";
    public static final String DELIMITER = ":";

    @Override
    public UserRequest decode(NativeWebRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION);
        validate(authorization);
        final String encoded = authorization.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(encoded);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        return new UserRequest(email, password);
    }


    private void validate(String authorization) {
        if (!authorization.startsWith(BASIC_TYPE)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보를 다시 확인해주세요.");
        }
    }
}
