package cart.dao;

import cart.dao.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Item> actorRowMapper =  (resultSet, rowNumber) -> {
        Item item = new Item(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")
        );
        return item;
    };

    public List<Item> findAll() {
        final String sql = "SELECT id, name, image_url, price FROM items ";
        return jdbcTemplate.query(sql, actorRowMapper);
    }
}
