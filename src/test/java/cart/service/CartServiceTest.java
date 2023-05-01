package cart.service;

import cart.JdbcMySqlDialectTest;
import cart.dao.CartDao;
import cart.dao.JdbcCartDao;
import cart.dto.CartResponse;
import cart.dto.CartResponses;
import cart.dto.CartSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        // when
        final Long savedId = 장바구니를_저장한다(2L, 3L, 3);

        // then
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    void 사용자의_id가_주어지면_해당_사용자의_장바구니_목록을_전체_조회한다() {
        // given
        final long userId = 1L;
        장바구니를_저장한다(userId, 2L, 2);
        장바구니를_저장한다(userId, 3L, 3);

        // when
        final CartResponses cartResponses = cartService.findAllByUserId(userId);

        // then
        final List<CartResponse> results = cartResponses.getCartResponses();
        assertAll(
                () -> assertThat(results).hasSize(2),
                () -> assertThat(results.get(0).getProductId()).isEqualTo(2L),
                () -> assertThat(results.get(0).getCount()).isEqualTo(2),
                () -> assertThat(results.get(1).getProductId()).isEqualTo(3L),
                () -> assertThat(results.get(1).getCount()).isEqualTo(3)
        );
    }

    private Long 장바구니를_저장한다(final long userId, final long productId, final int count) {
        final CartSaveRequest cartSaveRequest = new CartSaveRequest(userId, productId, count);
        return cartService.save(cartSaveRequest);
    }
}
