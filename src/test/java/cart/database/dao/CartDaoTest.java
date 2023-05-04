package cart.database.dao;

import cart.entity.CartItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;
    private Long userId;
    private Long productId;

    @BeforeEach
    public void setUp() {
        cartDao = new CartDao(jdbcTemplate);

        userId = 1L;
        productId = 1L;

        jdbcTemplate.update(
                "INSERT INTO CART (USER_ID, PRODUCT_ID, COUNT) VALUES (?, ?, ?)",
                userId, productId, 1
        );
    }

    @DisplayName("insert 테스트")
    @Test
    public void create() {
        Long newUserId = 2L;
        Long newProductId = 2L;

        cartDao.create(newUserId, newProductId);

        int count = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate, "CART", "USER_ID = " + newUserId + " AND PRODUCT_ID = " + newProductId
        );

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("userId로 cart 쿼리 테스트")
    @Test
    public void findByUserId() {
        List<CartItemEntity> result = cartDao.findByUserId(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
        assertThat(result.get(0).getProductId()).isEqualTo(productId);
        assertThat(result.get(0).getCount()).isEqualTo(1);
    }

    @DisplayName("userId와 cartId로 제거 테스트")
    @Test
    public void deleteByUserIdAndCartId() {
        final Long firstCartId = 1L;
        cartDao.deleteByUserIdAndCartId(userId, firstCartId);

        int count = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate, "CART", "USER_ID = " + userId + " AND ID = " + firstCartId
        );

        assertThat(count).isEqualTo(0);
    }
}
