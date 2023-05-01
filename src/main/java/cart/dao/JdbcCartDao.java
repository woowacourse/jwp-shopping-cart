package cart.dao;

import cart.dao.entity.Cart;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class JdbcCartDao implements CartDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCartDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final Cart cart) {
        final String sql = "INSERT INTO cart (user_id, product_id, count) VALUES (:userId, :productId, :count)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", cart.getUserId())
                .addValue("productId", cart.getProductId())
                .addValue("count", cart.getCount());

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Cart> findAllByUserId(final Long userId) {
        final String sql = "SELECT id, user_id, product_id, count, created_at FROM cart WHERE user_id = :userId";

        return jdbcTemplate.query(sql, Collections.singletonMap("userId", userId), createCartRowMapper());
    }

    private RowMapper<Cart> createCartRowMapper() {
        return (rs, rowNum) -> new Cart(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("product_id"),
                rs.getInt("count"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
