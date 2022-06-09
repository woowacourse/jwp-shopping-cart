package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DuplicateCartItemByProduct;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final RowMapper<Cart> CART_ROW_MAPPER = (resultSet, rowNum) ->
        new Cart(resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url"),
            resultSet.getInt("quantity"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public CartItemDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
            .withTableName("cart_item")
            .usingGeneratedKeyColumns("id");
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customer_id";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
            return namedParameterJdbcTemplate.queryForList(sql, parameters, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customer_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
        return namedParameterJdbcTemplate.queryForList(sql, parameters, Long.class);
    }

    public Long findProductIdById(final Long cartId) {
        final String sql = "SELECT product_id FROM cart_item WHERE id = :id";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource("id", cartId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, Long.class);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Cart addCartItem(final Long customerId, final Product product) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(:cusotomer_id, :product_id, :quantity)";

        MapSqlParameterSource parameters = new MapSqlParameterSource("customer_id", customerId);
        parameters.addValue("product_id", product.getId());
        parameters.addValue("quantity", 1);
        try {
            Long id = insertActor.executeAndReturnKey(parameters).longValue();
            return new Cart(id, product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), 1);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateCartItemByProduct();
        }
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

    public void updateCartItemQuantity(Long cartId, int quantity) {
        try {
            final String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id";
            MapSqlParameterSource parameters = new MapSqlParameterSource("quantity", quantity);
            parameters.addValue("id", cartId);
            namedParameterJdbcTemplate.update(sql, parameters);
        } catch (DataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Cart findByCartId(Long cartId) {
        final String sql =
            "SELECT ci.id, ci.product_id, p.name, p.price, p.image_url, ci.quantity\n"
                + "FROM cart_item AS ci JOIN product AS p on ci.product_id = p.id WHERE ci.id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource("id", cartId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, CART_ROW_MAPPER);
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

}
