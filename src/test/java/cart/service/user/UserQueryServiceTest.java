package cart.service.user;

import static cart.domain.user.UserFixture.NUNU_USER;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.user.User;
import cart.repository.user.StubUserRepository;
import cart.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class UserQueryServiceTest {

    private UserQueryService userQueryService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new StubUserRepository();
        userQueryService = new UserQueryService(userRepository);
    }

    @Test
    void 전체_조회_테스트() {
        userRepository.save(NUNU_USER);

        final List<User> result = userQueryService.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void 이메일로_조회_테스트() {
        userRepository.save(NUNU_USER);

        final Optional<User> result = userQueryService.findByEmail(NUNU_USER.getEmail().getValue());

        assertThat(result).isPresent();
    }

    @Test
    void 없는_이메일_조회_테스트() {
        final Optional<User> result = userQueryService.findByEmail(NUNU_USER.getEmail().getValue());

        assertThat(result).isEmpty();
    }
}
