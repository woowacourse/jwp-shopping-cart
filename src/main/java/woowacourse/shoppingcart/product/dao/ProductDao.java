package woowacourse.shoppingcart.product.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.product.exception.notfound.NotFoundProductException;
import woowacourse.shoppingcart.product.domain.Product;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    new Product(
                            productId,
                            resultSet.getString("name"), resultSet.getInt("price"),
                            resultSet.getString("image_url")
                    ), productId
            );
        } catch (final EmptyResultDataAccessException e) {
            throw new NotFoundProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("image_url")
                        ));
    }
}
