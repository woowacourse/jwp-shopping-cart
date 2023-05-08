package cart.dao;

import cart.domain.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    public JdbcProductDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> productEntityRowMapper = (resultSet, rowNum) -> Product.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image"),
            resultSet.getInt("price")
    );

    @Override
    @Transactional(readOnly = true)
    public List<Product> selectAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public Product selectById(final long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productEntityRowMapper, id);
    }

    @Override
    public long insert(final Product product) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public int update(final Product product) {
        String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getImage(), product.getPrice(), product.getId());
    }

    @Override
    public int deleteById(final long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
