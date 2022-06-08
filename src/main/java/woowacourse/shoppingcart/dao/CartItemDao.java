package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.exception.bodyexception.NotExistProductInCartException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Cart> rowMapper = (rs, rowNum) -> new Cart(rs.getLong("id"),
            toProduct(rs.getLong("product_id"), rs.getString("name"), rs.getInt("price"), rs.getString("image_url")),
            rs.getInt("quantity"));

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Product toProduct(Long productId, String name, int price, String imageUrl) {
        return new Product(productId, name, price, imageUrl);
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, 1);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean existProduct(Long customerId, Long productId) {
        final String query = "SELECT EXISTS(SELECT * FROM cart_item WHERE customer_id =? and product_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId);
    }

    public List<Cart> getCartsByCustomerId(Long customerId) {
        final String query = "SELECT c.id, c.customer_id, c.quantity, c.product_id, p.name, p.price, p.image_url "
                + "FROM cart_item c JOIN product p ON c.product_id = p.id WHERE c.customer_id = ?";
        return jdbcTemplate.query(query, rowMapper, customerId);
    }

    public Cart findCartByProductCustomer(Long customerId, Long productId) {
        try {
            final String query = "SELECT c.id, c.customer_id, c.quantity, c.product_id, p.name, p.price, p.image_url "
                    + "FROM cart_item c JOIN product p ON c.product_id = p.id WHERE c.customer_id = ? and c.product_id = ?";
            return jdbcTemplate.queryForObject(query, rowMapper, customerId, productId);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotExistProductInCartException();
        }
    }

    public void updateCartItem(Long customerId, int quantity, Long productId) {
        final String query = "UPDATE cart_item SET quantity =? WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(query, quantity, customerId, productId);
    }

    public void deleteCartItem(Long customerId, final Long productId) {
        final String query = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(query, customerId, productId);
    }
}
