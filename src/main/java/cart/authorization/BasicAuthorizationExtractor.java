package cart.authorization;

import cart.service.dto.UserDto;
import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthorizationExtractor {

    public static UserDto extract(final String header){
        if(header== null){
            throw new IllegalArgumentException("header가 비어있습니다");
        }

        String credentials = header.split("\\s")[1];
        byte[] bytes = Base64.decodeBase64(credentials);
        String[] emailAndPassword = new String(bytes).split(":");
        String email = emailAndPassword[0];
        String password = emailAndPassword[1];
        return new UserDto.Builder()
                .email(email)
                .password(password)
                .build();
    }
}
