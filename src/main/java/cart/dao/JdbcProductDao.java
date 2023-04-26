package cart.dao;

import cart.domain.Product;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image")
            );

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long insert(final Product product) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT * FROM product ORDER BY id ASC";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Product findById(long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (IncorrectResultSizeDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void update(Product product) {
        final String sql = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImage(), product.getId());
    }
}
