package cart.service;

import java.util.List;

import cart.domain.user.User;
import cart.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
