package cart.dao;

import cart.dao.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Transactional
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
        // when
        final Long savedId = 장바구니를_저장한다(1L, 1L, 3);

        // then
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    void 장바구니_전체_조회를_한다() {
        // given
        final long firstUserId = 1L;
        장바구니를_저장한다(firstUserId, 3L, 5);
        장바구니를_저장한다(firstUserId, 4L, 3);

        final long secondUserId = 2L;
        장바구니를_저장한다(secondUserId, 1L, 2);
        장바구니를_저장한다(secondUserId, 2L, 1);
        장바구니를_저장한다(secondUserId, 3L, 3);

        // when
        final List<Cart> firstUserCart = cartDao.findAllByUserId(firstUserId);
        final List<Cart> secondUserCart = cartDao.findAllByUserId(secondUserId);

        // then
        assertAll(
                () -> assertThat(firstUserCart).hasSize(2),
                () -> assertThat(secondUserCart).hasSize(3)
        );
    }

    @Test
    void 장바구니를_삭제한다() {
        // given
        final long userId = 1L;
        final Long savedId = 장바구니를_저장한다(userId, 2L, 3);

        // when
        cartDao.delete(savedId);

        // then
        assertThat(cartDao.findAllByUserId(1L)).isEmpty();
    }

    private Long 장바구니를_저장한다(final long userId, final long productId, final int count) {
        final Cart cart = new Cart(userId, productId, count);
        return cartDao.save(cart);
    }
}
