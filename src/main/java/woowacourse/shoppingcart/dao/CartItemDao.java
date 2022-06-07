package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItemEntity> ROW_MAPPER = (resultSet, rowNum) -> {
        Long customerId = resultSet.getLong("customer_id");
        Product product = new Product(resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url"));
        CartItem cartItem = new CartItem(product, resultSet.getInt("quantity"));
        return new CartItemEntity(customerId, cartItem);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartItemDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItemEntity> findAllByCustomerId(Long customerId) {
        final String sql = "SELECT C.customer_id, C.product_id, P.name, P.price, P.image_url, C.quantity "
                + "FROM cart_item AS C, product AS P "
                + "WHERE C.product_id = P.id AND C.customer_id = :customerId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);

        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public void save(CartItemEntity cartItem) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) "
                + "VALUES(:customerId, :productId, :quantity)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(cartItem);

        jdbcTemplate.update(sql, params);
    }

    public void updateByCustomerIdAndProductId(CartItemEntity cartItem) {
        final String sql = "UPDATE cart_item SET quantity = :quantity "
                + "WHERE customer_id = :customerId AND product_id = :productId";
        SqlParameterSource params = new BeanPropertySqlParameterSource(cartItem);

        jdbcTemplate.update(sql, params);
    }

    public void deleteAllByCustomerId(Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :customerId ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);

        jdbcTemplate.update(sql, params);
    }

    public void deleteByCustomerIdAndProductIds(Long customerId, List<Long> productIds) {
        final String sql = "DELETE FROM cart_item "
                + "WHERE customer_id = :customerId AND product_id in (:productIds)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("productIds", productIds);

        jdbcTemplate.update(sql, params);
    }
}
