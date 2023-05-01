package cart.service;

import cart.JdbcMySqlDialectTest;
import cart.dao.CartDao;
import cart.dao.JdbcCartDao;
import cart.dto.CartSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcMySqlDialectTest
@SuppressWarnings("NonAsciiCharacters")
class CartServiceTest {

    private CartService cartService;
    private CartDao cartDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        cartDao = new JdbcCartDao(jdbcTemplate);
        cartService = new CartService(cartDao, new CartMapper());
    }

    @Test
    void 장바구니를_저장한다() {
        // given
        final CartSaveRequest cartSaveRequest = new CartSaveRequest(2L, 3L, 3);

        // when
        final Long savedId = cartService.save(cartSaveRequest);

        // then
        assertThat(savedId).isEqualTo(1L);
    }
}
