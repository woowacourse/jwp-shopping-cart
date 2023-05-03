package cart.dao;

import cart.domain.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> actorMapper = (resultSet, rowNumber) -> new Cart(
            resultSet.getLong("user_id"),
            resultSet.getLong("item_id")
    );

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Cart cart) {
        final String sql = "INSERT INTO carts(user_id, item_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, cart.getUserId(), cart.getItemId());
    }

    public List<Cart> findBy(Long userId) {
        final String sql = "SELECT user_id, item_id FROM carts WHERE user_id = ?";
        return jdbcTemplate.query(sql, actorMapper, userId);
    }

    public void delete(Cart cart) {
        final String sql = "DELETE FROM carts WHERE user_id = ? AND item_id = ?";
        jdbcTemplate.update(sql, cart.getUserId(), cart.getItemId());
    }
}
