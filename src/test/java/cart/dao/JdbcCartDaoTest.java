package cart.dao;

import cart.dao.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@SuppressWarnings("NonAsciiCharacters")
class JdbcCartDaoTest {

    private CartDao cartDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        cartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    void 장바구니를_저장한다() {
        // given
        final Cart cart = new Cart(1L, 1L, 3);

        // when
        final Long savedId = cartDao.save(cart);

        // then
        assertThat(savedId).isEqualTo(1L);
    }
}
