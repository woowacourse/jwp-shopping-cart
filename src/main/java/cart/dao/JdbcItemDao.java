package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcItemDao implements ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final CreateItem createItem) {
        String sql = "insert into item(name, item_url, price) values (?, ?, ?)";

        jdbcTemplate.update(sql, createItem.getName(), createItem.getImageUrl(), createItem.getPrice());
    }

    @Override
    public List<Item> findAll() {
        String sql = "select * from item";

        return jdbcTemplate.query(sql, mapRow());
    }

    public RowMapper<Item> mapRow() {
        return (rs, rowNum) -> {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String itemUrl = rs.getString(3);
            int price = rs.getInt(4);

            return new Item(id, name, itemUrl, price);
        };
    }

    @Override
    public void update(final Long id, final CreateItem item) {
        String sql = "update item set name = ?, item_url = ?, price = ? where id = ?";

        jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), id);
    }

    @Override
    public void delete(final Long id) {
        String sql = "delete from item where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
