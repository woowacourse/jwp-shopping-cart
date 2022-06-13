package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Cart> cartRowMapper = (rs, rowNum) ->
            new Cart(
                    rs.getLong("id"),
                    new Product(
                            rs.getLong("product_id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getString("image_url")
                    ),
                    rs.getInt("quantity"),
                    rs.getBoolean("checked")
            );

    public CartItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Cart addCartItem(Long customerId, Cart cart) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", cart.getProduct().getId())
                .addValue("quantity", cart.getQuantity())
                .addValue("checked", cart.isChecked());

        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Cart(id, cart.getProduct(), cart.getQuantity(), cart.isChecked());
    }

    public boolean existByProductId(Long customerId, Long productId) {
        try {
            String sql = "SELECT EXISTS (SELECT * FROM cart_item WHERE customer_id = ? and product_id = ?)";
            return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public List<Cart> findByCustomerId(Long customerId) {
        try {
            String sql =
                    "SELECT c.id AS id, c.product_id AS product_id, p.name AS name, p.price AS price, "
                            + "p.image_url AS image_url, c.quantity AS quantity, c.checked AS checked "
                            + "FROM cart_item AS c  JOIN product AS p ON c.product_id = p.id "
                            + "WHERE customer_id = ?";
            return jdbcTemplate.query(sql, cartRowMapper, customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void updateCartItemByProductId(Long customerId, Cart cart) {
        String sql = "UPDATE cart_item SET quantity = ?, checked = ? WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(sql, cart.getQuantity(), cart.isChecked(), customerId, cart.getProduct().getId());
    }

    public void updateCartItems(List<Cart> carts) {
        String sql = "UPDATE cart_item SET quantity =?, checked = ? WHERE id = ?";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Cart cart = carts.get(i);
                        ps.setInt(1, cart.getQuantity());
                        ps.setBoolean(2, cart.isChecked());
                        ps.setLong(3, cart.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return carts.size();
                    }
                });
    }

    public void deleteCartItems(List<Long> cartIds) {
        String sql = "DELETE FROM cart_item WHERE id = ?";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, cartIds.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return cartIds.size();
                    }
                });
    }

    public void deleteAllCartItem(Long customerId) {
        String sql = "DELETE FROM cart_item WHERE customer_id = ?";

        int rowCount = jdbcTemplate.update(sql, customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
