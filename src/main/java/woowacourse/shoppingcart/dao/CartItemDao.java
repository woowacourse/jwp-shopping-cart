package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNum) ->
            new Cart(
                    resultSet.getLong("id"),
                    new Product(
                            resultSet.getLong("product_id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getString("image_url")
                    ),
                    resultSet.getInt("quantity")
            );

    public CartItemDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("CART_ITEM")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Long customerId, Long productId, Integer quantity) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);
        parameters.put("product_id", productId);
        parameters.put("quantity", quantity);

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Cart> findByCustomerId(Long customerId) {
        String sql = "SELECT * FROM CART_ITEM JOIN PRODUCT WHERE CART_ITEM.product_id = PRODUCT.id AND customer_id = ?";
        return jdbcTemplate.query(sql, cartRowMapper, customerId);
    }

    public Boolean existByCustomerIdAndProductId(Long customerId, Long productId) {
        final String query = "SELECT EXISTS (SELECT id FROM CART_ITEM WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId);
    }

    public void deleteByCustomerIdAndProductId(Long customerId, Long productId) {
        final String sql = "DELETE FROM CART_ITEM WHERE customer_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, customerId, productId);
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }
}
