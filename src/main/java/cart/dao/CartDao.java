package cart.dao;

import cart.domain.cart.Item;
import cart.domain.cart.ItemEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    private static RowMapper<ItemEntity> itemEntityRowMapper() {
        return (resultSet, rowNum) -> new ItemEntity(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
        );
    }

    public List<ItemEntity> findAll(final long memberId) {
        final String sql = "SELECT * FROM cart WHERE member_id = ?";

        return jdbcTemplate.query(sql, itemEntityRowMapper(), memberId);
    }

    public void insert(final Item item) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(item);

        simpleJdbcInsert.execute(source);
    }

    public void delete(final long itemId, final long memberId) {
        final String sql = "DELETE cart WHERE id = ? AND member_id = ?";

        jdbcTemplate.update(sql, itemId, memberId);
    }
}
