package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
@DisplayName("CartDao 클래스")
class CartDaoTest {

    private ProductDao productDao;
    private UserDao userDao;
    private CartDao cartDao;

    @Autowired
    void setUp(final JdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
        userDao = new UserDao(jdbcTemplate);
        cartDao = new CartDao(jdbcTemplate);
    }

    @Nested
    @DisplayName("product와 user가 저장되어 있을 때")
    class DescribeInsert {

        private long productId;

        @BeforeEach
        void setUp() {
            final ProductEntity productEntity = productDao.insert(new ProductEntity("odo", "url", 1));
            productId = productEntity.getId().getValue();
            userDao.insert(new UserEntity("a@a.com", "password1"));
        }

        @Test
        @DisplayName("Cart를 저장한다")
        void 생성_테스트() {
            final CartEntity result = cartDao.insert(new CartEntity("a@a.com", productId));
            assertAll(
                    () -> assertThat(result.getEmail()).isEqualTo("a@a.com"),
                    () -> assertThat(result.getProductId()).isEqualTo(productId)
            );
        }
    }
}
