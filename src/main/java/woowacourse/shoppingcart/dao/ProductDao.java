package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_MAPPER = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getInt("stock"),
                    resultSet.getString("image_url")
            );

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameters)
                .longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String sql = "SELECT id, name, price, stock, image_url FROM product WHERE id = :id";
            MapSqlParameterSource parameters = new MapSqlParameterSource("id", productId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, PRODUCT_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, stock, image_url FROM product";
        return namedParameterJdbcTemplate.query(sql, PRODUCT_MAPPER);
    }

    public void updateStock(Product product) {
        String sql = "UPDATE product SET stock = :stock WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", product.getId())
                .addValue("stock", product.getStock());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void delete(final Long productId) {
        final String sql = "DELETE FROM product WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", productId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
