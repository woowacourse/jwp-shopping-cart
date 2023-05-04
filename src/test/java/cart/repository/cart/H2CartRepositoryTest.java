package cart.repository.cart;

import static cart.domain.product.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Cart;
import cart.domain.user.User;
import cart.repository.product.H2ProductRepository;
import cart.repository.product.ProductRepository;
import cart.repository.user.H2UserRepository;
import cart.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class H2CartRepositoryTest {

    private User user = new User("asdf", "1234");
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private long userId;
    private long productId;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        userRepository = new H2UserRepository(jdbcTemplate);
        productRepository = new H2ProductRepository(jdbcTemplate);
        cartRepository = new H2CartRepository(jdbcTemplate, productRepository);
    }

    @BeforeEach
    void setUp() {
        userId = userRepository.save(user).getUserId().getValue();
        productId = productRepository.save(ODO_PRODUCT).getProductId().getValue();
        user = new User(userId, user);
    }

    @Test
    void 카트_저장_테스트() {
        final Cart cart = new Cart(user);

        Cart result = cartRepository.save(cart);

        Optional<Cart> findResult = cartRepository.findByUser(user);

        assertAll(
                ()->assertThat(findResult).isPresent()
        )
    }
}
