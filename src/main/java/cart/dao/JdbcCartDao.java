package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCartDao implements CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    private final RowMapper<CartEntity> cartEntityRowMapper = (resultSet, rowNum) ->
            new CartEntity(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("product_id")
            );

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Long insert(final Long userId, final Long productId) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("product_id", productId);

        return insertActor.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<CartEntity> findByUserId(final Long userId) {
        final String sql = "SELECT * FROM cart WHERE user_id = ?";
        return jdbcTemplate.query(sql, cartEntityRowMapper, userId);
    }
}
