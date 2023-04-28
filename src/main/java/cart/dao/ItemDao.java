package cart.dao;

import cart.domain.item.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class ItemDao {

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNum) -> new Item(
            resultSet.getLong("item_id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ITEM")
                .usingGeneratedKeyColumns("item_id");
    }

    public Item insert(Item item) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", item.getName());
        parameters.put("image_url", item.getImageUrl());
        parameters.put("price", item.getPrice());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Item(key.longValue(), item);
    }

    public Optional<Item> findById(Long id) {
        String sql = "SELECT item_id, name, image_url, price FROM ITEM WHERE item_id = ?";

        return jdbcTemplate.query(sql, actorRowMapper, id)
                .stream()
                .findAny();
    }

    public List<Item> findAll() {
        String sql = "SELECT item_id, name, image_url, price FROM ITEM";

        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public int update(Item item) {
        String sql = "UPDATE ITEM SET name = ?, image_url = ?, price = ? WHERE item_id = ?";

        return jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), item.getId());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM ITEM WHERE item_id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
