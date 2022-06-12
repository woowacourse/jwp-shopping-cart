package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    public static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("stock")
            );

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url, stock FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findByPage(final int limit, final int offset) {
        final String query = "SELECT id, name, price, image_url, stock FROM product ORDER BY id"
                + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER, limit, offset);
    }

    public int findTotalCount() {
        final String query = "SELECT COUNT(*) FROM product";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(query, int.class));
    }
}
