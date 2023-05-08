package cart.persistence.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.persistence.entity.Product;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Product> actorRowMapper = (resultSet, rowNum) -> new Product(
        resultSet.getLong("product_id"),
        resultSet.getString("name"),
        resultSet.getInt("price"),
        resultSet.getString("image_url")
    );

    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("product_id");
    }

    @Override
    public long save(final Product product) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Product findByName(final String name) {
        final String sql = "SELECT product_id, name, price, image_url FROM product WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, name);
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT product_id, name, price, image_url FROM product";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int update(final Product product) {
        final String sql = "UPDATE product SET name=?, price=?, image_url=? WHERE product_id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImageUrl(), product.getId());
    }

    @Override
    public int deleteById(final long id) {
        final String sql = "DELETE FROM product WHERE product_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(final long id) {
        final String sql = "SELECT EXISTS (SELECT * FROM product WHERE product_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }
}
