package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import cart.dao.UserDao;
import cart.domain.user.Email;
import cart.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class RdsUserRepositoryTest {

    private RdsUserRepository rdsUserRepository;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        rdsUserRepository = new RdsUserRepository(new UserDao(jdbcTemplate));
    }

    @Test
    void save() {
        final User user = new User("a@a.com", "password1");
        rdsUserRepository.save(user);
        final Optional<User> result = rdsUserRepository.findByEmail(new Email("a@a.com"));
        assertAll(
                () -> assertThat(result).isPresent(),
                () -> assertThat(result.get().getEmail().getValue()).isEqualTo("a@a.com"),
                () -> assertThat(result.get().getPassword()).isEqualTo("password1")
        );
    }

    @Nested
    class NotSaveTest {

        @BeforeEach
        void setUp() {
            rdsUserRepository.save(new User("a@a.com", "password1"));
            rdsUserRepository.save(new User("b@b.com", "password2"));
        }

        @Test
        void findAll() {
            final List<User> users = rdsUserRepository.findAll();
            assertAll(
                    () -> assertThat(users).hasSize(2),
                    () -> assertThat(users).contains(
                            new User("a@a.com", "password1"),
                            new User("b@b.com", "password2")
                    )
            );
        }

        @Test
        void findByEmail() {
            final Optional<User> user = rdsUserRepository.findByEmail(new Email("a@a.com"));
            assertAll(
                    () -> assertThat(user).isPresent(),
                    () -> assertThat(user.get().getEmail().getValue()).isEqualTo("a@a.com"),
                    () -> assertThat(user.get().getPassword()).isEqualTo("password1")
            );
        }
    }
}
