package cart.persistence.dao;

import cart.persistence.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCartDao implements Dao<CartEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<CartEntity> actorRowMapper = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong("user_id"),
            resultSet.getLong("product_id")
    );

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART")
                .usingGeneratedKeyColumns("cart_id");
    }

    @Override
    public Long save(final CartEntity cartEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<CartEntity> findById(final Long id) {
        final String sql = "SELECT user_id, product_id FROM cart WHERE cart_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, id));
    }

    @Override
    public List<CartEntity> findAll() {
        return null;
    }

    @Override
    public int update(CartEntity cartEntity) {
        return 0;
    }

    @Override
    public int deleteById(long id) {
        final String sql = "DELETE FROM cart WHERE cart_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CartEntity> findProductsByUser(String email) {
        return null;
    }

    public int deleteByUserAndProductId(Long userId, Long id) {
        final String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, userId, id);
    }
}
