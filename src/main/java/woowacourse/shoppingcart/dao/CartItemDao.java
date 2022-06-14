package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (resultSet, rowNum) ->
        new CartItem(resultSet.getLong("id"),
            resultSet.getInt("quantity"),
            new Product(
                resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
            ));
    private static final int DEFAULT_QUANTITY = 1;


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public CartItemDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
            .withTableName("cart_item")
            .usingGeneratedKeyColumns("id");
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customer_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
        return namedParameterJdbcTemplate.queryForList(sql, parameters, Long.class);
    }

    public Long findProductIdById(final Long cartId) {
        final String sql = "SELECT product_id FROM cart_item WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", cartId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Long.class);
    }

    public CartItem addCartItem(final Long customerId, final Product product) {
        MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
        parameters.addValue("product_id", product.getId());
        parameters.addValue("quantity", DEFAULT_QUANTITY);
        Long id = insertActor.executeAndReturnKey(parameters).longValue();

        return new CartItem(id, DEFAULT_QUANTITY, product);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        final int rowCount = namedParameterJdbcTemplate.update(sql, parameters);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public int findQuantityById(Long cartIds) {
        try {
            final String sql = "SELECT quantity FROM cart_item WHERE id = :id";
            MapSqlParameterSource parameters = new MapSqlParameterSource("id", cartIds);
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }

    }

    public void updateCartItemQuantity(Long customerId, Long cartId, int quantity) {
        try {
            final String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id AND customer_id = :customerId";
            MapSqlParameterSource parameters = new MapSqlParameterSource("quantity", quantity);
            parameters.addValue("id", cartId);
            parameters.addValue("customerId", customerId);
            namedParameterJdbcTemplate.update(sql, parameters);
        } catch (DataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public CartItem findByCartId(Long cartId) {
        final String sql =
            "SELECT ci.id, ci.product_id, p.name, p.price, p.image_url, ci.quantity\n"
                + "FROM cart_item AS ci JOIN product AS p on ci.product_id = p.id WHERE ci.id = :id";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource("id", cartId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, CART_ITEM_ROW_MAPPER);
        } catch (DataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCart(Long customerId) {
        final String sql = "DELETE FROM cart_item where customer_id = :customer_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public boolean existByProduct(Long customerId, Long productId) {
        final String sql = "SELECT EXISTS (SELECT 1 FROM cart_item WHERE customer_id = :customer_id AND product_id = :product_id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
        parameters.addValue("product_id", productId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class) != 0;
    }

    public Cart findCartByCustomerId(final Long customerId) {
        final String sql =
            "SELECT ci.id, ci.product_id, p.name, p.price, p.image_url, ci.quantity\n"
                + "FROM cart_item AS ci JOIN product AS p on ci.product_id = p.id WHERE ci.customer_id = :customerId";
        MapSqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId);
        return new Cart(namedParameterJdbcTemplate.query(sql, parameters, CART_ITEM_ROW_MAPPER));
    }
}
