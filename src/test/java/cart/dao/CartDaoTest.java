package cart.dao;

import cart.domain.cart.Item;
import cart.domain.cart.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql("classpath:test.sql")
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setup() {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    void 전체_상품_조회() {
        final List<ItemEntity> items = cartDao.findAll(1);

        assertThat(items.size()).isEqualTo(3);
    }

    @Test
    void 상품_삽입() {
        final int memberId = 2;
        final Item item = new Item(memberId, 1);

        cartDao.insert(item);

        assertThat(cartDao.findAll(memberId).size()).isEqualTo(1);
    }

    @Test
    void 상품_삭제() {
        final long memberId = 1;
        final long itemId = 1;
        cartDao.delete(itemId, memberId);

        final List<ItemEntity> userOneRemainItems = cartDao.findAll(1);

        assertThat(userOneRemainItems.size()).isEqualTo(2);
    }
}
