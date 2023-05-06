package cart.service;

import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.service.dto.UserSaveDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserSaveDto userSaveDto) {
        User user = User.createToSave(
                userSaveDto.getEmail(),
                userSaveDto.getPassword(),
                userSaveDto.getName(),
                userSaveDto.getPhoneNumber()
        );
        this.userRepository.save(user);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
