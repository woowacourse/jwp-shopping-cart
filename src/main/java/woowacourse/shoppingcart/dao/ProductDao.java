package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.product.Product;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public Optional<Product> findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
            return Optional.of(jdbcTemplate.queryForObject(query, getProductRowMapper(), productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, getProductRowMapper());
    }

    private RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNumber) ->
                new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"), resultSet.getInt("price"),
                        resultSet.getString("image_url")
                );
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
