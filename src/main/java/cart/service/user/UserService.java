package cart.service.user;

import cart.domain.user.Email;
import cart.domain.user.Password;
import cart.domain.user.User;
import cart.service.user.dto.UserDto;
import cart.service.user.dto.UserLoginDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public UserDto login(UserLoginDto loginDto) {
        Email email = Email.from(loginDto.getEmail());
        Password password = Password.from(loginDto.getPassword());

        User user = userRepository.findByEmailAndPassword(email, password);

        return new UserDto(user);
    }
}
