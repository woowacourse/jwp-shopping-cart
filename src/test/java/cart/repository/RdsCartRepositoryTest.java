package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import cart.controller.dto.CartResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.UserDao;
import cart.domain.cart.Cart;
import cart.domain.user.Email;
import cart.entiy.CartEntity;
import cart.entiy.ProductEntity;
import cart.entiy.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class RdsCartRepositoryTest {

    private RdsCartRepository rdsCartRepository;
    private CartDao cartDao;
    private ProductDao productDao;
    private UserDao userDao;

    @Autowired
    void setUp(final JdbcTemplate jdbcTemplate) {
        cartDao = new CartDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        userDao = new UserDao(jdbcTemplate);
        rdsCartRepository = new RdsCartRepository(cartDao, productDao);
    }

    @Nested
    @DisplayName("product와 user가 저장되어 있을 때")
    class DescribeSave {

        private long productId;

        @BeforeEach
        void setUp() {
            final ProductEntity productEntity = productDao.insert(new ProductEntity("odo", "url", 1));
            productId = productEntity.getId().getValue();
            userDao.insert(new UserEntity("a@a.com", "password1"));
        }

        @Nested
        @DisplayName("cart를 저장하면")
        class ContextSave {

            private long cartId;

            @BeforeEach
            void setUp() {
                final CartResponse cartResponse = rdsCartRepository.save(
                        "a@a.com",
                        productId
                );
                cartId = cartResponse.getCartId();
            }

            @Test
            @DisplayName("이메일로 조회할 수 있다")
            void findByEmail() {
                final List<Cart> carts = rdsCartRepository.findByEmail(new Email("a@a.com"));
                assertAll(
                        () -> assertThat(carts).hasSize(1),
                        () -> assertThat(carts.get(0).getCartId()).isEqualTo(cartId),
                        () -> assertThat(carts.get(0).getProduct().getId()).isEqualTo(productId),
                        () -> assertThat(carts.get(0).getProduct().getName().getValue()).isEqualTo("odo"),
                        () -> assertThat(carts.get(0).getProduct().getImage().getValue()).isEqualTo("url"),
                        () -> assertThat(carts.get(0).getProduct().getPrice().getValue()).isEqualTo(1)
                );
                assertThat(carts).hasSize(1);
            }

            @Test
            @DisplayName("아이디로 조회할 수 있다")
            void findById() {
                final Optional<CartEntity> cartEntity = rdsCartRepository.findById(cartId);
                assertAll(
                        () -> assertThat(cartEntity).isPresent(),
                        () -> assertThat(cartEntity.get().getCartId()).isPositive(),
                        () -> assertThat(cartEntity.get().getEmail()).isEqualTo("a@a.com"),
                        () -> assertThat(cartEntity.get().getProductId()).isEqualTo(productId)
                );
            }
        }
    }
}
