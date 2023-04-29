package cart.dao;

import cart.domain.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    private final RowMapper<Cart> actorRowMapper = (resultSet, rowNum) -> new Cart(
            resultSet.getLong("cart_id"),
            new User(
                    resultSet.getLong("user_id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            ),
            new Item(
                    resultSet.getLong("item_id"),
                    resultSet.getString("name"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("price")
            )
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CARTS")
                .usingGeneratedKeyColumns("cart_id");
    }

    public Cart insert(User user, Item item) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("item_id", item.getId());
        parameters.put("user_email", user.getEmail());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Cart(key.longValue(), user, item);
    }

    public List<Cart> findByEmailAndItemId(String email, Long itemId) {
        String sql = "SELECT c.cart_id, u.user_id, u.email, u.password, i.item_id, i.name, i.image_url, i.price "
                + "FROM CARTS c "
                + "inner join users u on u.email = c.user_email "
                + "inner join items i on i.item_id = c.item_id "
                + "WHERE u.email = ? AND i.item_id = ?";

        return jdbcTemplate.query(sql, actorRowMapper, email, itemId);
    }

    public List<Cart> findAllByEmail(String email) {
        String sql = "SELECT c.cart_id, u.user_id, u.email, u.password, i.item_id, i.name, i.image_url, i.price "
                + "FROM CARTS c "
                + "inner join users u on u.email = c.user_email "
                + "inner join items i on i.item_id = c.item_id "
                + "WHERE u.email = ?";

        return jdbcTemplate.query(sql, actorRowMapper, email);
    }

    public int delete(Long id, String email) {
        String sql = "DELETE FROM CARTS WHERE cart_id = ? AND user_email = ?";

        return jdbcTemplate.update(sql, id, email);
    }
}
