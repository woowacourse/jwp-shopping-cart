package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public Item findById(Long itemId) {
        String sql = "select * from item where id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, mapRow(), itemId);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private RowMapper<Item> mapRow() {
        return (rs, rowNum) -> {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String itemUrl = rs.getString(3);
            int price = rs.getInt(4);

            return new Item(id, name, itemUrl, price);
        };
    }

    @Override
    public int update(final Long id, final CreateItem item) {
        String sql = "update item set name = ?, item_url = ?, price = ? where id = ?";

        return jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), id);
    }

    @Override
    public int delete(final Long id) {
        String sql = "delete from item where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isItemExists(Long itemId) {
        String sql = "select exists(select id from item where id = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, itemId);    }
}
