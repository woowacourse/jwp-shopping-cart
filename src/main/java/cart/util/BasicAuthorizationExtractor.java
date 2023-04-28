package cart.util;

import cart.dto.member.MemberResponseDto;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<MemberResponseDto> {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public MemberResponseDto extractHeader(final String authorization) {
        if (!IsBasicAuthorization(authorization) || authorization.isEmpty()) {
            throw new IllegalArgumentException("Basic 인증이 올바른지 확인해주세요.");
        }

        String authHeaderValue = authorization.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        
        String email = credentials[0];
        String password = credentials[1];

        return MemberResponseDto.from(email, password);
    }

    private boolean IsBasicAuthorization(final String authorization) {
        return authorization.toLowerCase().startsWith(BASIC_TYPE.toLowerCase());
    }
}
