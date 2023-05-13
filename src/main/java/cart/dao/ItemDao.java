package cart.dao;

import cart.dao.entity.ItemEntity;
import cart.domain.Item;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ItemDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ItemEntity> actorRowMapper = (resultSet, rowNum) -> {
        ItemEntity itemEntity = new ItemEntity(
                resultSet.getLong("item_id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")
        );
        return itemEntity;
    };

    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Item item) {
        String sql = "INSERT INTO ITEM(name, image_url, price) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"item_id"});
            ps.setString(1, item.getName());
            ps.setString(2, item.getImageUrl());
            ps.setInt(3, item.getPrice());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<ItemEntity> findById(Long id) {
        String sql = "SELECT item_id, name, image_url, price FROM ITEM WHERE item_id = ?";

        return jdbcTemplate.query(sql, actorRowMapper, id)
                .stream()
                .findAny();
    }

    public List<ItemEntity> findAll() {
        String sql = "SELECT item_id, name, image_url, price FROM ITEM";

        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public void update(Long id, Item item) {
        String sql = "UPDATE ITEM SET name = ?, image_url = ?, price = ? WHERE item_id = ?";

        jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM ITEM WHERE item_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
