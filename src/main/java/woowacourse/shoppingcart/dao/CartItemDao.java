package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ROW_MAPPER = (rs, rowNum) -> new CartItem(
            rs.getLong("product_id"),
            rs.getString("product_name"),
            rs.getInt("product_price"),
            rs.getInt("quantity"),
            rs.getString("product_image_url")
    );

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate template;

    public CartItemDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long saveCartItem(final Long customerId, final Long productId) {
        SqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", 1);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameter).longValue();
    }

    public List<CartItem> findAllByCustomerId(Long customerId) {
        String query =
                "SELECT p.id AS product_id, p.name AS product_name, p.price AS product_price, ca.quantity, p.image_url AS product_image_url"
                        + " FROM cart_item AS ca"
                        + " LEFT JOIN product as p ON p.id = ca.product_id"
                        + " WHERE ca.customer_id = :customerId";
        SqlParameterSource nameParameters = new MapSqlParameterSource("customerId", customerId);

        return template.query(query, nameParameters, CART_ROW_MAPPER);
    }

    public void updateQuantity(Long customerId, Long productId, int quantity) {
        String query = "UPDATE cart_item SET quantity = :quantity WHERE customer_id = :customer_id AND product_id = :product_id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("quantity", quantity)
                .addValue("customer_id", customerId)
                .addValue("product_id", productId);

        template.update(query, nameParameters);
    }

    public void deleteCartItem(Long customerId, Long productId) {
        String query = "DELETE FROM cart_item WHERE customer_id = :customer_id AND product_id = :product_id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("customer_id", customerId)
                .addValue("product_id", productId);

        template.update(query, nameParameters);
    }

    public boolean existCartItems(Long customerId, Long productId) {
        String query = "SELECT EXISTS (SELECT * FROM cart_item WHERE customer_id = :customer_id AND product_id = :product_id)";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("customer_id", customerId)
                .addValue("product_id", productId);

        int count = template.queryForObject(query, nameParameters, Integer.class);
        return count != 0;
    }
}
