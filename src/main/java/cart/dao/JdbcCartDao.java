package cart.dao;

import cart.dao.entity.Cart;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
}
