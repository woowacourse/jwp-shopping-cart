package cart.auth;


import cart.domain.entity.MemberEntity;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
public class BasicAuthenticationExtractor implements AuthenticationExtractor<MemberEntity> {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public MemberEntity extract(NativeWebRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            return MemberEntity.of(email, password);
        }

        return null;
    }

}
