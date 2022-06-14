package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> ROW_MAPPER =
            (resultSet, rowNum) -> new CartItem(
                    resultSet.getLong("id"),
                    resultSet.getLong("customer_id"),
                    toProduct(resultSet),
                    resultSet.getInt("quantity")
            );

    private static Product toProduct(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url"));
    }


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CartItemDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final CartItem cartItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", cartItem.getId());
        params.put("customer_id", cartItem.getCustomerId());
        params.put("product_id", cartItem.getProduct().getId());
        params.put("quantity", cartItem.getQuantity());
        return jdbcInsert.executeAndReturnKey(params)
                .longValue();
    }

    public List<CartItem> findByCustomerId(final Long customerId) {
        final String sql = "SELECT c.id, c.customer_id, c.quantity, "
                + "c.product_id, p.name, p.price, p.image_url FROM cart_item c "
                + "JOIN product p ON c.product_id = p.id where c.customer_id = (:id)";

        final SqlParameterSource parameters = new MapSqlParameterSource("id", customerId);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }

    public void deleteCartItemsByCustomerId(long customerId, List<Long> productIds) {
        final String sql = "DELETE FROM cart_item where customer_id = (:customerId) and product_id = (:productId)";

        Map<String, Object>[] batchInputs = new HashMap[productIds.size()];
        int count = 0;
        for (long productId : productIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("customerId", customerId);
            map.put("productId", productId);
            batchInputs[count++] = map;
        }
        jdbcTemplate.batchUpdate(sql, batchInputs);
    }

    public void update(Long customerId, Long productId, int quantity) {
        final String sql = "UPDATE cart_item SET quantity=(:quantity) WHERE customer_id = (:customerId) and "
                + "product_id = (:productId)";

        final SqlParameterSource parameters = new MapSqlParameterSource("quantity", quantity)
                .addValue("customerId", customerId)
                .addValue("productId", productId);
        jdbcTemplate.update(sql, parameters);
    }

    public boolean existProduct(long customerId, long productId) {
        final String sql = "SELECT EXISTS("
                + "SELECT * FROM cart_item WHERE customer_id = (:customerId) AND product_id = (:productId))";

        final SqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId)
                .addValue("productId", productId);
        return jdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }
}
