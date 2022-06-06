package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getInt("stock_quantity"),
                    new ThumbnailImage(
                            resultSet.getString("url"),
                            resultSet.getString("alt")
                    )
            );

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("stock_quantity", product.getStockQuantity());
        params.put("url", product.getThumbnailImage().getUrl());
        params.put("alt", product.getThumbnailImage().getAlt());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, stock_quantity, url, alt FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, stock_quantity, url, alt FROM product";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
