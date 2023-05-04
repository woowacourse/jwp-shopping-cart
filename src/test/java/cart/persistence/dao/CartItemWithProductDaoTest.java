package cart.persistence.dao;

import cart.persistence.entity.CartItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@ContextConfiguration(classes = {CartItemWithProductDao.class, CartItemDao.class})
class CartItemWithProductDaoTest {

    @Autowired
    CartItemWithProductDao cartItemWithProductDao;
    @Autowired
    CartItemDao cartItemDao;

    @BeforeEach
    void setUp() {
        var entity1 = new CartItemEntity(1L, 1L, 1L);
        var entity2 = new CartItemEntity(2L, 1L, 1L);
        var entity3 = new CartItemEntity(3L, 2L, 1L);
        List<CartItemEntity> entities = List.of(entity1, entity2, entity3);

        cartItemDao.insertCartItems(entities);
    }

    @Test
    @DisplayName("CartId가 1인 cartItem을 조회하면 row는 2이다")
    void findProductsByCartId() {
        assertThat(cartItemWithProductDao.findProductsByCartId(1L).size()).isEqualTo(2);
    }
}
