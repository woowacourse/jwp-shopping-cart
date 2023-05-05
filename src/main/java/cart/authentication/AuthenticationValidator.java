package cart.authentication;

import cart.controller.dto.AuthInfo;
import cart.domain.user.User;
import cart.exception.auth.NotValidTokenInfoException;
import cart.repository.UserRepository;

public class AuthenticationValidator {

    private static final String NOT_VALID_INFO_MESSAGE = "사용자 정보가 유효하지 않습니다.";
    private final UserRepository userRepository;

    public AuthenticationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(AuthInfo authInfo) {
        User user = userRepository.findByEmail(authInfo.getEmail())
                .orElseThrow(() -> new NotValidTokenInfoException(NOT_VALID_INFO_MESSAGE));

        if (!user.matches(authInfo.getPassword())) {
            throw new NotValidTokenInfoException(NOT_VALID_INFO_MESSAGE);
        }
    }
}
