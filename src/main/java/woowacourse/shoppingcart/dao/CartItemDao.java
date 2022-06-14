package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final int DEFAULT_QUANTITY_VALUE = 1;

    private static final RowMapper<Cart> CART_ROW_MAPPER = (resultSet, rowNum) ->
            new Cart(resultSet.getLong("id"),
                    resultSet.getLong("product_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("quantity")
            );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public CartItemDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";

        MapSqlParameterSource parameter = new MapSqlParameterSource("customerId", customerId);

        return namedParameterJdbcTemplate.query(sql, parameter, (rs, rowNum) -> rs.getLong("id"));
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";

            MapSqlParameterSource parameter = new MapSqlParameterSource("cartId", cartId);

            return namedParameterJdbcTemplate.queryForObject(sql, parameter, (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId)
                .addValue("productId", productId)
                .addValue("quantity", DEFAULT_QUANTITY_VALUE);

        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";

        MapSqlParameterSource parameter = new MapSqlParameterSource("id", id);

        final int rowCount = namedParameterJdbcTemplate.update(sql, parameter);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteAllCartItem(final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :customerId";

        MapSqlParameterSource parameter = new MapSqlParameterSource("customerId", customerId);

        final int rowCount = namedParameterJdbcTemplate.update(sql, parameter);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void updateQuantity(Long cartId, int quantity) {
        final String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :cartId";

        MapSqlParameterSource parameters = new MapSqlParameterSource("quantity", quantity)
                .addValue("cartId", cartId);

        final int rowCount = namedParameterJdbcTemplate.update(sql, parameters);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public Cart findCartById(Long cartId) {
        final String sql = "SELECT cart_item.id, "
                + "cart_item.product_id, product.name, "
                + "product.price, product.image_url, "
                + "cart_item.quantity "
                + "FROM cart_item "
                + "JOIN product ON cart_item.product_id = product.id "
                + "WHERE cart_item.id = :cartId";

        MapSqlParameterSource parameter = new MapSqlParameterSource("cartId", cartId);

        return namedParameterJdbcTemplate.queryForObject(sql, parameter, CART_ROW_MAPPER);
    }

    public List<Cart> findCartsByCustomerId(Long customerId) {
        final String sql = "SELECT cart_item.id, "
                + "cart_item.product_id, "
                + "product.name, "
                + "product.price, "
                + "product.image_url, "
                + "cart_item.quantity "
                + "FROM cart_item "
                + "JOIN product ON cart_item.product_id = product.id "
                + "WHERE cart_item.customer_id = :customerId";

        MapSqlParameterSource parameter = new MapSqlParameterSource("customerId", customerId);

        return namedParameterJdbcTemplate.query(sql, parameter, CART_ROW_MAPPER);
    }

    public boolean existByProductId(Long productId, Long customerId) {
        final String query = "SELECT EXISTS (SELECT 1 FROM cart_item WHERE product_id = :productId AND customer_id = :customerId)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("productId", productId)
                .addValue("customerId", customerId);

        return namedParameterJdbcTemplate.queryForObject(query, parameters, Integer.class) != 0;
    }
}
