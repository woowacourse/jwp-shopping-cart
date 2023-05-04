package cart.persistence.dao;

import cart.persistence.entity.CartItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@JdbcTest
@ContextConfiguration(classes = {CartItemDao.class})
class CartItemDaoTest {

    @Autowired
    CartItemDao cartItemDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    List<CartItemEntity> entities;

    @BeforeEach
    void setUp() {
        var entity1 = new CartItemEntity(1L, 1L, 1L);
        var entity2 = new CartItemEntity(2L, 1L, 1L);
        var entity3 = new CartItemEntity(3L, 2L, 1L);
        entities = List.of(entity1, entity2, entity3);
    }

    @Test
    @DisplayName("size가 3인 CartItemEntity를 insert하고 이를 조회한 row는 3이다")
    void insertCartItemsTest() {
        cartItemDao.insertCartItems(entities);

        int rows = countRowsInTable(jdbcTemplate, "cart_item");
        assertThat(rows).isEqualTo(3);
    }

    @Test
    @DisplayName("cartId가 1인 cartItem을 모두 삭제하고 이를 조회하면 row는 0이다")
    void deleteByCartIdTest() {
        cartItemDao.insertCartItems(entities);
        cartItemDao.deleteByCartId(1L);

        int rows = countRowsInTableWhere(jdbcTemplate, "cart_item", "cart_id = 1");
        assertThat(rows).isEqualTo(0);
    }
}
