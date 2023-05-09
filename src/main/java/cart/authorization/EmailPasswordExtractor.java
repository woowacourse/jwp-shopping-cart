package cart.authorization;

import cart.service.UserService;
import cart.service.dto.UserDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class EmailPasswordExtractor {
    private static UserService userService;
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    public EmailPasswordExtractor(UserService userService) {
        EmailPasswordExtractor.userService = userService;
    }

    public static UserDto extract(final String header) throws IllegalAccessException {
        if (header == null) {
            throw new IllegalArgumentException("header가 비어있습니다");
        }
        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];
            Long id = userService.findLoginUserId(email);
            return new UserDto.Builder()
                    .id(id)
                    .email(email)
                    .password(password)
                    .build();
        }

        throw new IllegalAccessException("Basic 응답이 아닙니다.");
    }
}
