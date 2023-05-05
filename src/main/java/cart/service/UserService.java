package cart.service;

import cart.domain.user.User;
import cart.exception.user.SignInFailureException;
import cart.repository.UserRepository;
import cart.service.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private static final String SIGN_IN_FAILURE_MESSAGE = "로그인에 실패했습니다.";

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public UserDto signIn(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SignInFailureException(SIGN_IN_FAILURE_MESSAGE));

        if (!user.matches(password)) {
            throw new SignInFailureException(SIGN_IN_FAILURE_MESSAGE);
        }

        return new UserDto(user);
    }
}
