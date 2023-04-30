package cart.product.dao;

import cart.product.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2ProductDao implements ProductDao {

    private static final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("image_url"),
            rs.getInt("price")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2ProductDao(final JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Product insert(final Product product) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate()).withTableName("products")
                                                                                                                    .usingGeneratedKeyColumns("id");
        final Number key = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(product));
        long id = key.longValue();
        return new Product(id, product.getName(), product.getImageUrl(), product.getPrice());
    }

    @Override
    public int update(final Product product) {
        final String sql = "UPDATE products SET name = :name, image_url = :imageUrl, price = :price WHERE id = :id";

        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(product));
    }

    @Override
    public Optional<Product> findById(final Long id) {
        final String sql = "SELECT id, name, image_url, price FROM products WHERE id = :id";
        final MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            Product product = namedParameterJdbcTemplate.queryForObject(sql, params, productRowMapper);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT id, name, image_url, price FROM products";

        return namedParameterJdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public int deleteById(final Long id) {
        final String sql = "DELETE FROM products WHERE id = :id";

        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}
