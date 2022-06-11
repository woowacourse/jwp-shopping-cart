package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;
import woowacourse.shoppingcart.domain.product.Product;

@Repository
public class CartItemDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Long> idRowMapper = (rs, rowNum) -> rs.getLong("id");
    private final RowMapper<Long> productIdRowMapper = (rs, rowNum) -> rs.getLong("product_id");

    private final RowMapper<CartItem> cartItemRowMapper = (resultSet, rowNumber) ->
            new CartItem(
                    resultSet.getLong("product_id"),
                    new Name(resultSet.getString("name")),
                    new Price(resultSet.getInt("price")),
                    new ImageUrl(resultSet.getString("image_url"))
            );


    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id =:customerId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), productIdRowMapper);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), idRowMapper);
    }

    public Optional<Long> findProductIdById(final Long cartId) {
        final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("cartId", cartId);
        List<Long> query = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters),
                productIdRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(query));
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);
        parameters.put("product_id", productId);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public int deleteCartItem(Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :customerId AND product_id = :productId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        parameters.put("productId", productId);
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }

    public int deleteAllCartItems(Long customerId, final List<Long> productIds) {
        String sql = "DELETE FROM cart_item WHERE  customer_id = :customerId AND product_id = :productId";
        Map<String, Object>[] batchValues = toBatchValues(customerId, productIds);
        return namedParameterJdbcTemplate.batchUpdate(sql, batchValues).length;
    }

    private Map<String, Object>[] toBatchValues(Long customerId, List<Long> productIds) {
        Map<String, Object>[] batchValues = new Map[productIds.size()];
        for (int i = 0; i < productIds.size(); i++) {
            Map<String, Object> parameters = new MapSqlParameterSource()
                    .addValue("customerId", customerId)
                    .addValue("productId", productIds.get(i))
                    .getValues();
            batchValues[i] = parameters;
        }
        return batchValues;
    }

    public List<CartItem> findCartItemsByCustomerId(long customerId) {
        String sql = "SELECT DISTINCT p.id product_id, p.name name, p.price price, p.image_url image_url FROM cart_item ci "
                + "JOIN product p ON ci.product_id=p.id "
                + "WHERE ci.customer_id=:customerId";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, sqlParameterSource, cartItemRowMapper);
    }
}
