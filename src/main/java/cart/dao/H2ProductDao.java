package cart.dao;

import cart.domain.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2ProductDao implements ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
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
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "INSERT INTO products (name, image_url, price) VALUES (:name, :imageUrl, :price)",
                new BeanPropertySqlParameterSource(product),
                keyHolder,
                new String[]{"id"}
        );
        long id = keyHolder.getKey().longValue();
        return new Product(id, product.getName(), product.getImageUrl(), product.getPrice());
    }

    @Override
    public int update(final Product product) {
        return namedParameterJdbcTemplate.update("UPDATE products SET name = :name, image_url = :imageUrl, price = :price WHERE id = :id", new BeanPropertySqlParameterSource(product));
    }

    @Override
    public Optional<Product> findById(final Long id) {
        Product product = namedParameterJdbcTemplate.queryForObject(
                "SELECT id, name, image_url, price FROM products WHERE id = :id",
                new MapSqlParameterSource("id", id),
                PRODUCT_ROW_MAPPER
        );
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        return namedParameterJdbcTemplate.query(
                "SELECT id, name, image_url, price FROM products",
                PRODUCT_ROW_MAPPER
        );
    }

    @Override
    public int deleteById(final Long id) {
        return namedParameterJdbcTemplate.update("DELETE FROM products WHERE id = :id", new MapSqlParameterSource("id", id));
    }
}
