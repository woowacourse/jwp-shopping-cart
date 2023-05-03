package cart.repository.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.user.User;
import cart.entiy.cart.CartEntity;
import cart.entiy.user.UserEntityId;
import cart.repository.user.H2UserRepository;
import cart.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class CartDaoTest {

    private CartDao cartDao;
    private UserRepository userRepository;
    private long userId;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        cartDao = new CartDao(jdbcTemplate);
        userRepository = new H2UserRepository(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        userId = userRepository.save(new User("asdf", "1234")).getUserId().getValue();
    }

    @Test
    void 저장_테스트() {
        final CartEntity result = cartDao.save(new CartEntity(null, userId));

        assertAll(
                () -> assertThat(result.getCartId().getValue()).isPositive(),
                () -> assertThat(result.getUserEntityId().getValue()).isEqualTo(userId)
        );
    }

    @Nested
    class NotSaveTest {

        private long cartId;

        @BeforeEach
        void setUp() {
            cartId = cartDao.save(new CartEntity(null, userId)).getCartId().getValue();
        }

        @Test
        void 조회_테스트() {
            final CartEntity result = cartDao.findByUserId(new UserEntityId(userId));

            assertAll(
                    () -> assertThat(result.getCartId().getValue()).isEqualTo(cartId),
                    () -> assertThat(result.getUserEntityId().getValue()).isEqualTo(userId)
            );
        }
    }
}
