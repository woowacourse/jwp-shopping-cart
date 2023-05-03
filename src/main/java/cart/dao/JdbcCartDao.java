package cart.dao;

import cart.dao.entity.Cart;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class JdbcCartDao implements CartDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCartDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final Cart cart) {
        final String sql = "INSERT INTO cart (user_id, product_id) VALUES (:userId, :productId)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", cart.getUserId())
                .addValue("productId", cart.getProductId());
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public int delete(Long userId, Long productId) {
        final String sql = "DELETE FROM cart " +
                "WHERE user_id = :userId AND product_id = :productId";

        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("productId", productId);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public int deleteByProductId(final Long productId) {
        final String sql = "DELETE FROM cart WHERE product_id = :productId";
        return jdbcTemplate.update(sql, Collections.singletonMap("productId", productId));
    }
}
