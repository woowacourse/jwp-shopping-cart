package cart.dao;

import cart.domain.cart.Item;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public List<Item> findAll(final long userId) {
        final String sql = "SELECT * FROM cart WHERE user_id = ?";

        return jdbcTemplate.query(sql, itemRowMapper(), userId);
    }

    public long insert(final Item item) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(item);

        return simpleJdbcInsert.executeAndReturnKey(source).longValue();
    }

    public void delete(final long id) {
        final String sql = "DELETE cart WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Item> itemRowMapper() {
        return (resultSet, rowNum) -> new Item(
                resultSet.getLong("id"),
                resultSet.getLong("user_id")
                , resultSet.getLong("product_id")
        );
    }
}
