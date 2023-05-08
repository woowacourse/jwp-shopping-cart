package cart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cart.entiy.CartEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final RowMapper<CartEntity> cartEntityRowMapper = (resultSet, rowNum) -> {
        Long cartId = resultSet.getLong("cart_id");
        String email = resultSet.getString("email");
        Long productId = resultSet.getLong("product_id");
        return new CartEntity(cartId, email, productId);
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("cart_id");
    }

    public CartEntity insert(final CartEntity cartEntity) {
        final Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("email", cartEntity.getEmail());
        parameters.put("product_id", cartEntity.getProductId());
        final long cartId = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)).longValue();
        return new CartEntity(cartId, cartEntity);
    }

    public List<CartEntity> findByEmail(final String email) {
        final String sql = "SELECT cart_id, email, product_id FROM CART WHERE email=?";
        return jdbcTemplate.query(sql, cartEntityRowMapper, email);
    }


    public Optional<CartEntity> findById(final Long id) {
        final String sql = "SELECT cart_id, email, product_id FROM CART WHERE cart_id=?";
        try {
            final CartEntity result = jdbcTemplate.queryForObject(sql, cartEntityRowMapper, id);
            return Optional.of(result);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM CART WHERE cart_id=?";
        jdbcTemplate.update(sql, id);
    }
}
