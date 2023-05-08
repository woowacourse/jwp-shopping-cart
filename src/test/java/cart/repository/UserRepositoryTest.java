package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import cart.domain.user.User;
import cart.repository.dao.UserDao;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    UserDao userDao;

    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(userDao);
    }

    @Test
    @DisplayName("사용자를 조회한다.")
    void findByEmailSuccessWithExistsEmail() {
        given(userDao.findByEmail(anyString())).willReturn(Optional.of(new User("a@a.com", "a")));

        Optional<User> actual = userRepository.findByEmail("a@a.com");

        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("모든 사용자를 조회한다.")
    void findAllSuccess() {
        given(userDao.findAll()).willReturn(Collections.emptyList());

        List<User> actual = userRepository.findAll();

        assertThat(actual).isNotNull();
    }
}
