package cart.dao;

import cart.domain.TestFixture;
import cart.domain.service.UserRepository;
import cart.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import({UserRepositoryImpl.class, UserDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class UserRepositoryImplTest {
    @Autowired
    private UserRepository userRepository;

    @DisplayName("User 저장 테스트")
    @Test
    void createUser() {
        User user = TestFixture.ZUNY;
        String email = user.getEmail();

        userRepository.save(user);

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(1);
        assertThat(allUsers).extractingResultOf("getEmail")
                .contains(email);
    }

    @DisplayName("User 전체 조회 테스트")
    @Test
    void findAllUsers() {
        User userA = TestFixture.ZUNY;
        User userB = TestFixture.ADMIN;

        userRepository.save(userA);
        userRepository.save(userB);

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(2);
        assertThat(allUsers).extractingResultOf("getEmail")
                .containsExactlyInAnyOrder(userA.getEmail(), userB.getEmail());
    }

}
