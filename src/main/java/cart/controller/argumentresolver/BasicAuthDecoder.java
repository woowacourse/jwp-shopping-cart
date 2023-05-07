package cart.controller.argumentresolver;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BasicAuthDecoder implements AuthDecoder {

    public static final String BASIC_TYPE = "Basic";


    @Override
    public String decode(String authorization) {
        validate(authorization);
        final String encoded = authorization.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(encoded);
        return new String(decodedBytes);
    }


    private void validate(String authorization) {
        if (!authorization.startsWith(BASIC_TYPE)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보를 다시 확인해주세요.");
        }
    }
}
