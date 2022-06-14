package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.ResourceNotFoundException;

@Repository
public class ProductDao {

    private static final RowMapper<Product> ROW_MAPPER = (resultSet, rowNumber) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product selectById(final Long productId) {
        String query = "select id, name, price, image_url from product where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", productId);
        try {
            return namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("존재하지 않는 상품입니다.");
        }
    }

    public List<Product> selectProductsOfPage(final int page, final int limit) {
        int offset = (page - 1) * limit;
        String query = "select id, name, price, image_url from product orders LIMIT :limit OFFSET :offset";
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return namedParameterJdbcTemplate.query(query, params, ROW_MAPPER);
    }

    public List<Product> selectAll() {
        String query = "select id, name, price, image_url from product";
        return namedParameterJdbcTemplate.query(query, ROW_MAPPER);
    }
}
