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
                            resultSet.getString("image_url"),
                            resultSet.getInt("price")
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
        String sql = "SELECT EXISTS (SELECT id FROM CART_ITEM WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId);
    }

    public Integer findQuantityByCustomerIdAndProductId(Long customerId, Long productId) {
        String sql = "SELECT quantity FROM CART_ITEM WHERE customer_id = ? AND product_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, customerId, productId);
        } catch (EmptyResultDataAccessException exception) {
            return 0;
        }
    }

    public void updateQuantityByCustomerIdAndProductId(Long customerId, Long productId, Integer quantity) {
        String sql = "UPDATE CART_ITEM SET quantity = ? WHERE customer_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, quantity, customerId, productId);
    }

    public void deleteByCustomerIdAndProductId(Long customerId, Long productId) {
        final String sql = "DELETE FROM CART_ITEM WHERE customer_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, customerId, productId);
    }
}
