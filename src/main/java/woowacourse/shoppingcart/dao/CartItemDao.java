package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (rs, rowNum) -> new CartItem(
      rs.getLong("id"),
      new Customer(
        rs.getLong("customer_id"),
        rs.getString("email"),
        rs.getString("nickname"),
        rs.getString("password")
      ),
      new Product(
        rs.getLong("product_id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getString("image_url")
      ),
      rs.getInt("quantity")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public CartItemDao(JdbcTemplate jdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<CartItem> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT * FROM cart_item ci "
          + "JOIN product p ON ci.id = p.id "
          + "JOIN customer c ON ci.id = c.id "
          + "WHERE ci.customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_ROW_MAPPER, customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(Long customerId, Long productId) {
        final String query = "DELETE FROM cart_item WHERE customer_id = :customer_id AND product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
          .addValue("customer_id", customerId)
          .addValue("product_id", productId);
        namedParameterJdbcTemplate.update(query, parameters);
    }

    public void updateQuantityById(Long customerId, int quantity, Long productId) {
        final String query = "UPDATE cart_item SET quantity = :quantity WHERE customer_id = :customer_id AND product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
          .addValue("quantity", quantity)
          .addValue("customer_id", customerId)
          .addValue("product_id", productId);
        namedParameterJdbcTemplate.update(query, parameters);
    }
}
