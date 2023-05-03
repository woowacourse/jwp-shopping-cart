package cart.repository.user;

import static cart.domain.user.UserFixture.NUNU_USER;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class H2UserRepositoryTest {

    private H2UserRepository h2UserRepository;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        h2UserRepository = new H2UserRepository(jdbcTemplate);
    }

    @Test
    void 저장_테스트() {
        final User result = h2UserRepository.save(NUNU_USER);

        assertThat(result.getUserId().getValue()).isPositive();
    }

    @Nested
    class NotSaveTest {

        private long userId;

        @BeforeEach
        void setUp() {
            userId = h2UserRepository.save(NUNU_USER).getUserId().getValue();
        }

        @Test
        void 존재_확인_테스트() {
            final boolean result = h2UserRepository.existsByEmailAndPassword(
                    NUNU_USER.getEmail().getValue(),
                    NUNU_USER.getPassword().getValue());

            assertThat(result).isTrue();
        }

        @Test
        void 없는거_확인_테스트() {
            final boolean result = h2UserRepository.existsByEmailAndPassword(
                    NUNU_USER.getEmail().getValue() + "1",
                    NUNU_USER.getPassword().getValue());

            assertThat(result).isFalse();
        }

        @Test
        void 이메일로_조회_테스트() {
            final User result = h2UserRepository.findByEmail(NUNU_USER.getEmail().getValue()).get();

            assertThat(result).usingRecursiveComparison().isEqualTo(new User(userId, NUNU_USER));
        }
    }
}
