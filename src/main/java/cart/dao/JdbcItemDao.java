package cart.dao;

import cart.domain.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcItemDao implements ItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final Item item) {
        String sql = "insert into item(name, item_url, price) values (?, ?, ?, ?)";

        jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice());
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public void update(final Long id, final Item item) {

    }

    @Override
    public void delete(final Long id) {

    }
}
