package cart.repository.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.product.ProductFixture;
import cart.domain.user.User;
import cart.entiy.cart.CartEntity;
import cart.entiy.cart.CartEntityId;
import cart.entiy.cart.CartProductEntity;
import cart.repository.product.H2ProductRepository;
import cart.repository.product.ProductRepository;
import cart.repository.user.H2UserRepository;
import cart.repository.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class CartProductDaoTest {

    private CartProductDao cartProductDao;
    private CartDao cartDao;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private long userId;
    private long cartId;
    private long productId;


    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        cartProductDao = new CartProductDao(jdbcTemplate);
        userRepository = new H2UserRepository(jdbcTemplate);
        productRepository = new H2ProductRepository(jdbcTemplate);
        cartDao = new CartDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        userId = userRepository.save(new User("asdf", "1234")).getUserId().getValue();
        cartId = cartDao.save(new CartEntity(null, userId)).getCartId().getValue();
        productId = productRepository.save(ProductFixture.ODO_PRODUCT).getProductId().getValue();
    }

    @Test
    void 저장_테스트() {
        final CartProductEntity cartProductEntity = new CartProductEntity(null, cartId, productId);
        cartProductDao.insertAll(List.of(cartProductEntity));

        final List<CartProductEntity> result = cartProductDao.findAllByCartId(new CartEntityId(1L));

        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getCartEntityId().getValue()).isEqualTo(cartId),
                () -> assertThat(result.get(0).getProductEntityId().getValue()).isEqualTo(productId)
        );
    }

    @Nested
    class NotSaveTest {

        @BeforeEach
        void setUp() {
            cartProductDao.insertAll(List.of(new CartProductEntity(null, cartId, productId)));
        }

        @Test
        void 조회_테스트() {
            final List<CartProductEntity> result = cartProductDao.findAllByCartId(new CartEntityId(cartId));

            assertAll(
                    () -> assertThat(result).hasSize(1),
                    () -> assertThat(result.get(0).getCartProductEntityId().getValue()).isPositive(),
                    () -> assertThat(result.get(0).getCartEntityId().getValue()).isEqualTo(cartId),
                    () -> assertThat(result.get(0).getProductEntityId().getValue()).isEqualTo(productId)
            );
        }

        @Test
        void 제거_테스트() {
            cartProductDao.deleteAllByCartId(new CartEntityId(cartId));

            final List<CartProductEntity> result = cartProductDao.findAllByCartId(new CartEntityId(cartId));

            assertThat(result).isEmpty();
        }
    }
}
