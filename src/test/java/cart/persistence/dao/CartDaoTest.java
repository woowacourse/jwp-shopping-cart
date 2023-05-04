package cart.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@ContextConfiguration(classes = {CartDao.class})
class CartDaoTest {

    @Autowired
    CartDao cartDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartDao.createCart(1L);
    }

    @Test
    void createCart() {
        cartDao.createCart(2L);
        cartDao.createCart(3L);

        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "cart");
        assertThat(rows).isEqualTo(3);
    }

    @Test
    void existsByCartId() {
        assertThat(cartDao.existsByCartId(1L)).isTrue();
    }

    @Test
    void findCartIdByMemberId() {
        assertThat(cartDao.findCartIdByMemberId(1L)).isNotEmpty();
    }
}
