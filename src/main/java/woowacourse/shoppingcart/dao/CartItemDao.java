package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DataNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static Cart rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Cart(
                rs.getLong("id"),
                new Product(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                ),
                rs.getInt("quantity")
        );
    }

    public Long addCartItem(final Long customerId, final Long productId, final Integer quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) " +
                "VALUES(:customer_id, :product_id, :quantity)";

        final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customer_id";
        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        return namedParameterJdbcTemplate.query(sql, params, ((rs, rowNum) -> rs.getLong("product_id")));
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customer_id";
        final Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);

        return namedParameterJdbcTemplate.query(sql, params, ((rs, rowNum) -> rs.getLong("id")));
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
            final Map<String, Object> params = new HashMap<>();
            params.put("cartId", cartId);

            return namedParameterJdbcTemplate.queryForObject(sql, params,
                    (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException();
        }
    }

    public List<Cart> findAllByCustomerId(final Long id) {
        final String sql = "SELECT CI.id, CI.product_id, P.name, P.price, P.image_url, CI.quantity " +
                "FROM cart_item CI JOIN product P ON CI.product_id = P.id WHERE CI.customer_id = :id";
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return namedParameterJdbcTemplate.query(sql, params, CartItemDao::rowMapper);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        final int rowCount = namedParameterJdbcTemplate.update(sql, params);
        if (rowCount == 0) {
            throw new DataNotFoundException();
        }
    }

    public void deleteAllById(final List<Long> ids) {
        final SqlParameterSource params = new MapSqlParameterSource("ids", ids);
        final String sql = "DELETE FROM cart_item WHERE id IN (:ids)";

        namedParameterJdbcTemplate.update(sql, params);
    }

    public Optional<Cart> findCartByCustomerIdAndProductId(final Long customerId, final Long productId) {
        final String sql = "SELECT CI.id, CI.product_id, P.name, P.price, P.image_url, CI.quantity " +
                "FROM cart_item CI JOIN product P ON CI.product_id = P.id " +
                "WHERE CI.customer_id = :customerId AND P.id = :productId";

        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);

        final List<Cart> carts = namedParameterJdbcTemplate.query(sql, params, CartItemDao::rowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(carts));
    }

    public void update(final Cart cart, final Long customerId) {
        final String sql = "UPDATE cart_item SET quantity = :quantity " +
                "WHERE customer_id = :customerId AND product_id = :productId";

        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", cart.getProductId());
        params.put("quantity", cart.getQuantity());


        namedParameterJdbcTemplate.update(sql, params);
    }

    public boolean existsInCart(final Long customerId, final Long productId) {
        final String sql = "SELECT EXISTS (SELECT * FROM cart_item " +
                "WHERE customer_id = :customerId AND product_id = :productId)";

        final Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);

        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class));
    }

    public List<Cart> findAllCartsByIds(final List<Long> cartIds) {
        final String sql = "SELECT CI.id, CI.product_id, P.name, P.price, P.image_url, CI.quantity " +
                "FROM cart_item CI JOIN product P ON CI.product_id = P.id " +
                "WHERE CI.id IN (:cartIds)";

        final Map<String, Object> params = new HashMap<>();
        params.put("cartIds", cartIds);

        return namedParameterJdbcTemplate.query(sql, params, CartItemDao::rowMapper);
    }
}
