package cart.repository;

import cart.domain.user.Email;
import cart.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class RdsUserRepository implements UserRepository {

    @Override
    public User findByEmail(final Email email) {
        if (email.equals(new Email("a@a.com"))) {
            return new User("a@a.com", "password1");
        }
        return new User("b@b.com", "password2");
    }
}
