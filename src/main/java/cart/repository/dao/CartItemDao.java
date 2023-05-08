package cart.repository.dao;

import cart.domain.cart.Cart;
import cart.domain.item.Item;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    public void insertCartItem(Cart cart) {
        MapSqlParameterSource[] insertParameters = cart.getItems()
                .stream()
                .map(item -> new MapSqlParameterSource()
                        .addValue("cart_id", cart.getId())
                        .addValue("item_id", item.getId()))
                .toArray(MapSqlParameterSource[]::new);

        simpleJdbcInsert.executeBatch(insertParameters);
    }

    public List<Item> findAllByCartId(Long cartId) {
        String sql = "SELECT i.id, i.name, i.image_url, i.price "
                + "FROM CART_ITEM ci "
                + "INNER JOIN ITEMS i ON i.id = ci.item_id "
                + "WHERE cart_id = ?";

        return jdbcTemplate.query(sql, actorRowMapper, cartId);
    }

    public void deleteByCartId(Long cartId) {
        String sql = "DELETE FROM CART_ITEM WHERE cart_id = ?";

        jdbcTemplate.update(sql, cartId);
    }
}
