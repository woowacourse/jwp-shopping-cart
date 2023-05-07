package cart.persistence.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import(CartDao.class)
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
    @DisplayName("Cart를 추가한다")
    void createCart() {
        cartDao.createCart(2L);
        cartDao.createCart(3L);

        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "cart");
        assertThat(rows).isEqualTo(3);
    }

    @Test
    @DisplayName("cartId에 해당하는 cart가 존재하는지 판단한다")
    void existsByCartId() {
        assertThat(cartDao.existsByCartId(1L)).isTrue();
    }

    @Test
    @DisplayName("MemeberId에 해당하는 CartId를 찾는다")
    void findCartIdByMemberId() {
        assertThat(cartDao.findCartIdByMemberId(1L)).isNotEmpty();
    }
}
