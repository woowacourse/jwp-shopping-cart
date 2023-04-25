package cart.dao;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductDao implements ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("image"),
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
                "INSERT INTO products (name, image, price) VALUES (:name, :image, :price)",
                new BeanPropertySqlParameterSource(product),
                keyHolder,
                new String[]{"id"}
        );
        long id = keyHolder.getKey().longValue();
        return new Product(id, product.getName(), product.getImage(), product.getPrice());
    }

    @Override
    public void update(final Product product) {
        namedParameterJdbcTemplate.update(
                "UPDATE products SET name = :name, image = :image, price = :price WHERE id = :id",
                new BeanPropertySqlParameterSource(product)
        );
    }

    @Override
    public Optional<Product> findById(final Long id) {
        Product product = namedParameterJdbcTemplate.queryForObject(
                "SELECT id, name, image, price FROM products WHERE id = :id",
                new MapSqlParameterSource("id", id),
                PRODUCT_ROW_MAPPER
        );
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        return namedParameterJdbcTemplate.query(
                "SELECT id, name, image, price FROM products",
                PRODUCT_ROW_MAPPER
        );
    }

    @Override
    public void deleteById(final Long id) {
        namedParameterJdbcTemplate.update(
                "DELETE FROM products WHERE id = :id",
                new MapSqlParameterSource("id", id)
        );
    }
}
