package cart.dao;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
class JdbcTemplateCartDaoTest {

    private CartDao cartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        cartDao = new JdbcTemplateCartDao(jdbcTemplate);
        userDao = new JdbcTemplateUserDao(jdbcTemplate);
        productDao = new JdbcTemplateProductDao(jdbcTemplate);
    }

    @DisplayName("selectByUserId 테스트")
    @Test
    void selectByUserIdTest() {
        int userId = userDao.insert(new UserEntity(null, "1", "1"));
        int productId1 = productDao.insert(new ProductEntity(null, "치킨", 10000, "image_url"));
        int productId2 = productDao.insert(new ProductEntity(null, "피자", 12000, "image_url"));

        cartDao.insert(new CartEntity(userId, productId1));
        cartDao.insert(new CartEntity(userId, productId2));

        List<CartEntity> carts = cartDao.selectByUserId(userId);

        assertSoftly(softly -> {
            softly.assertThat(carts).hasSize(2);
            softly.assertThat(carts.get(0).getProductId()).isEqualTo(productId1);
            softly.assertThat(carts.get(1).getProductId()).isEqualTo(productId2);
        });
    }

    @DisplayName("delete 테스트")
    @Test
    void deleteTest() {
        int userId = userDao.insert(new UserEntity(null, "1", "1"));
        int productId = productDao.insert(new ProductEntity(null, "치킨", 10000, "image_url"));

        cartDao.insert(new CartEntity(userId, productId));
        cartDao.delete(new CartEntity(userId, productId));

        Assertions.assertThat(cartDao.selectByUserId(userId)).isEmpty();
    }
}
