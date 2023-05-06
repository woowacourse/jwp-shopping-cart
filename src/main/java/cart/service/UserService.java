package cart.service;

import cart.domain.cart.CartRepository;
import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.service.dto.UserAuthDto;
import cart.service.dto.UserSaveDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public void save(UserSaveDto userSaveDto) {
        User user = User.createToSave(
                userSaveDto.getEmail(),
                userSaveDto.getPassword(),
                userSaveDto.getName(),
                userSaveDto.getPhoneNumber()
        );
        Long userId = this.userRepository.save(user);
        this.cartRepository.create(userId);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User signUp(UserAuthDto userAuthDto) {
        User user = this.userRepository.findByEmail(userAuthDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 유저가 존재하지 않습니다." + System.lineSeparator() +
                        "존재하지 않는 이메일: " + userAuthDto.getEmail()));
        if (user.checkPassword(userAuthDto.getPassword())) {
            return user;
        }
        throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
    }
}
