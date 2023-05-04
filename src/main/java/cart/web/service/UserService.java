package cart.web.service;

import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.web.controller.user.dto.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean isLoggedIn(final UserRequest userRequest) {
        return userRepository.isExist(userRequest.getEmail(), userRequest.getPassword());
    }
}
