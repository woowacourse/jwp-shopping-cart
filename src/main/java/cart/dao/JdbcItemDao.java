package cart.dao;

import cart.domain.Item;
import cart.entity.ItemEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcItemDao implements ItemDao {

    public static final String TABLE_NAME = "item";
    public static final String KEY_COLUMN_NAME = "id";

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;


    public JdbcItemDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME)
                .usingColumns("name", "item_url", "price");
    }

    @Override
    public ItemEntity save(final Item item) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", item.getName())
                .addValue("item_url", item.getImageUrl())
                .addValue("price", item.getPrice());

        long itemId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new ItemEntity(itemId,item.getName(),item.getImageUrl(), item.getPrice());
    }

    @Override
    public List<ItemEntity> findAll() {
        String sql = "select * from item";

        return jdbcTemplate.query(sql, mapRow());
    }

    public RowMapper<ItemEntity> mapRow() {
        return (rs, rowNum) -> {
            Long id = rs.getLong(1);
            String name = rs.getString(2);
            String itemUrl = rs.getString(3);
            int price = rs.getInt(4);

            return new ItemEntity(id, name, itemUrl, price);
        };
    }

    @Override
    public void update(final Long id, final Item item) {
        String sql = "update item set name = ?, item_url = ?, price = ? where id = ?";

        jdbcTemplate.update(sql, item.getName(), item.getImageUrl(), item.getPrice(), id);
    }

    @Override
    public void delete(final Long id) {
        String sql = "delete from item where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
