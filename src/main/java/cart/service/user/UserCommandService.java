package cart.service.user;

import cart.domain.user.User;
import cart.event.user.UserRegisteredEvent;
import cart.repository.user.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserCommandService(final UserRepository userRepository,
            final ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public User save(final String email, final String password) {
        final User user = userRepository.save(new User(email, password));
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(user));
        return user;
    }
}
