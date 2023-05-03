package cart.service;

import cart.JdbcMySqlDialectTest;
import cart.dao.CartDao;
import cart.dao.JdbcCartDao;
import cart.dao.JdbcCartProductDao;
import cart.dto.CartSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcMySqlDialectTest
@SuppressWarnings("NonAsciiCharacters")
class CartServiceTest {

    private CartService cartService;
    private JdbcCartProductDao jdbcCartProductDao;
    private CartDao cartDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        cartDao = new JdbcCartDao(jdbcTemplate);
        jdbcCartProductDao = new JdbcCartProductDao(jdbcTemplate);
        cartService = new CartService(cartDao, jdbcCartProductDao, new CartMapper());
    }

    @Test
    void 장바구니를_저장한다() {
        // when
        final Long savedId = 장바구니를_저장한다(2L, 3L);

        // then
        assertThat(savedId).isEqualTo(1L);
    }

    @Nested
    class 삭제_테스트를_한다 {

        @Test
        void 장바구니_id가_주어지면_장바구니를_삭제_한다() {
            // given
            장바구니를_저장한다(2L, 3L);

            // when
            cartService.delete(2L, 3L);

            // then
            assertThat(cartService.findAllByUserId(2L).getCartResponses()).isEmpty();
        }

        @Test
        void 장바구니_id가_없는_id면_장바구니를_삭제에_실패한다() {
            // when, then
            assertThatThrownBy(() -> cartService.delete(1000L, 1000L))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    private Long 장바구니를_저장한다(final long userId, final long productId) {
        final CartSaveRequest cartSaveRequest = new CartSaveRequest(userId, productId);
        return cartService.save(cartSaveRequest);
    }
}
