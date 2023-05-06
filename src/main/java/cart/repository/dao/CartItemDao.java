package cart.repository.dao;

import cart.domain.item.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartItemDao {

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNum) -> new Item(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART_ITEM")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(Long cartId, Long itemId) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("cart_id", cartId);
        parameters.put("item_id", itemId);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return key.longValue();
    }

    public List<Item> findAllByCartId(Long cartId) {
        String sql = "SELECT i.id, i.name, i.image_url, i.price "
                + "FROM CART_ITEM ci "
                + "INNER JOIN ITEMS i ON i.id = ci.item_id "
                + "WHERE cart_id = ?";

        return jdbcTemplate.query(sql, actorRowMapper, cartId);
    }

    public int delete(Long cartId, Long itemId) {
        String sql = "DELETE FROM CART_ITEM WHERE cart_id = ? AND item_id = ?";

        return jdbcTemplate.update(sql, cartId, itemId);
    }
}
