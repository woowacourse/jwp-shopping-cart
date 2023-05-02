package cart.dao;

import cart.JdbcMySqlDialectTest;
import cart.dao.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcMySqlDialectTest
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
    void 장바구니를_삭제한다() {
        // given
        final long userId = 1L;
        final Long savedId = 장바구니를_저장한다(userId, 2L, 3);

        // when
        cartDao.delete(savedId);

        // then
        assertThat(장바구니_단건_조회_한다(savedId)).isNotPresent();
    }

    private Long 장바구니를_저장한다(final long userId, final long productId, final int count) {
        final Cart cart = new Cart(userId, productId, count);
        return cartDao.save(cart);
    }

    private Optional<Cart> 장바구니_단건_조회_한다(final long cartId) {
        final String sql = "select * from cart where id =:id";

        RowMapper<Cart> cartRowMapper = (rs, rowNum) -> new Cart(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("product_id"),
                rs.getInt("count"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );


        try {
            final Cart cart = jdbcTemplate.queryForObject(sql, Map.of("id", cartId), cartRowMapper);
            return Optional.of(cart);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
