package cart.authorization;

import cart.service.UserService;
import cart.service.dto.UserDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor {
    private static UserService userService;
    @Autowired
    public BasicAuthorizationExtractor(UserService userService) {
        this.userService = userService;
    }

    public static UserDto extract(final String header){
        if(header== null){
            throw new IllegalArgumentException("header가 비어있습니다");
        }

        String credentials = header.split("\\s")[1];
        byte[] bytes = Base64.decodeBase64(credentials);
        String[] emailAndPassword = new String(bytes).split(":");
        String email = emailAndPassword[0];
        String password = emailAndPassword[1];

        Long id = userService.findLoginUserId(email);
        return new UserDto.Builder()
                .id(id)
                .email(email)
                .password(password)
                .build();
    }
}
