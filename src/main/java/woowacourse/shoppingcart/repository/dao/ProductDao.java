package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")
            );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = :id";
            Map<String, Object> params = new HashMap<>();
            params.put("id", productId);
            return jdbcTemplate.queryForObject(query, params, PRODUCT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findByPage(final int pageNumber, final int limitCount) {
        final int startProductIndex = (pageNumber - 1) * limitCount;
        final String query = "SELECT id, name, price, image_url FROM product ORDERS LIMIT :limitCount OFFSET :pageNumber";
        Map<String, Object> params = new HashMap<>();
        params.put("limitCount", limitCount);
        params.put("pageNumber", startProductIndex);
        return jdbcTemplate.query(query, params, PRODUCT_ROW_MAPPER);
    }

    public List<Product> findAll() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }
}
