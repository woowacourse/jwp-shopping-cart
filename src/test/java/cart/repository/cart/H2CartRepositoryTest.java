package cart.repository.cart;

import static cart.domain.product.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.repository.product.H2ProductRepository;
import cart.repository.product.ProductRepository;
import cart.repository.user.H2UserRepository;
import cart.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class H2CartRepositoryTest {

    private Product product = ODO_PRODUCT;
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
        product = new Product(productId, product);
    }

    @Test
    void 카트_저장_테스트() {
        final Cart cart = new Cart(user);

        final Cart result = cartRepository.save(cart);

        final Optional<Cart> findResult = cartRepository.findByUser(user);

        assertAll(
                () -> assertThat(findResult).isPresent(),
                () -> assertThat(findResult).contains(result),
                () -> assertThat(findResult.get().getCartProducts().getCartProducts()).isEmpty(),
                () -> assertThat(findResult.get().getUser()).isEqualTo(user),
                () -> assertThat(findResult.get().getCartId()).isEqualTo(result.getCartId())
        );
    }

    @Test
    void 카트_저장_테스트_카트_상품_추가() {
        final Cart cart = new Cart(user);
        cart.addProduct(product);

        final Cart result = cartRepository.save(cart);

        final Optional<Cart> findResult = cartRepository.findByUser(user);

        assertAll(
                () -> assertThat(findResult).isPresent(),
                () -> assertThat(findResult).contains(result),
                () -> assertThat(findResult.get().getCartProducts().getCartProducts()).hasSize(1),
                () -> assertThat(findResult.get().getUser()).isEqualTo(user),
                () -> assertThat(findResult.get().getCartId()).isEqualTo(result.getCartId()),
                () -> assertThat(findResult.get().getCartProducts().getCartProducts().get(0).getProduct()).isEqualTo(
                        product),
                () -> assertThat(findResult.get().getCartProducts().getCartProducts().get(0).getCartProductId())
                        .isEqualTo(result.getCartProducts().getCartProducts().get(0).getCartProductId())
        );
    }

    @Nested
    class NotSaveTest {

        private Cart cart;

        @BeforeEach
        void setUp() {
            final Cart newCart = new Cart(user);
            newCart.addProduct(product);

            cart = cartRepository.save(newCart);
        }

        @Test
        void 조회_테스트() {
            final Optional<Cart> findResult = cartRepository.findByUser(user);

            assertAll(
                    () -> assertThat(findResult).isPresent(),
                    () -> assertThat(findResult).contains(cart),
                    () -> assertThat(findResult.get().getCartProducts().getCartProducts()).hasSize(1),
                    () -> assertThat(findResult.get().getUser()).isEqualTo(user),
                    () -> assertThat(findResult.get().getCartId()).isEqualTo(cart.getCartId()),
                    () -> assertThat(
                            findResult.get().getCartProducts().getCartProducts().get(0).getProduct()).isEqualTo(
                            product),
                    () -> assertThat(findResult.get().getCartProducts().getCartProducts().get(0).getCartProductId())
                            .isEqualTo(cart.getCartProducts().getCartProducts().get(0).getCartProductId())
            );
        }
    }
}
