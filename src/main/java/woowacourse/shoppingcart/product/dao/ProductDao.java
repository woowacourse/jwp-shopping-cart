package woowacourse.shoppingcart.product.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> rowMapper = (resultSet, rowNumber) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url")
    );

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, rowMapper, productId);
        } catch (final EmptyResultDataAccessException e) {
            throw new NotFoundProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, rowMapper);
    }

    public boolean existProduct(final Long id) {
        final String query = "SELECT EXISTS(SELECT id FROM product WHERE id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, id);
    }
}
