package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.cart.Item;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

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
        final List<Item> items = cartDao.findAll(1);

        assertThat(items.size()).isEqualTo(3);
    }

    @Test
    void 상품_삽입() {
        final Item item = new Item(2, 1);

        final long insertedId = cartDao.insert(item);

        assertThat(insertedId).isEqualTo(4);
    }

    @Test
    void 상품_삭제() {
        cartDao.delete(1);
        final List<Item> userOneRemainItems = cartDao.findAll(1);

        assertThat(userOneRemainItems.size()).isEqualTo(2);
    }
}
