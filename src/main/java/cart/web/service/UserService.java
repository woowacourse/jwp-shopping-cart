package cart.web.service;

import cart.domain.user.User;
import cart.domain.user.UserRepository;
import cart.web.controller.cart.dto.AuthCredentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public boolean isExistUser(final AuthCredentials authCredentials) {
        final Optional<User> userOptional = userRepository.findUserByEmailAndPassword(
                authCredentials.getEmail(), authCredentials.getPassword());
        return userOptional.isPresent();
    }
}
