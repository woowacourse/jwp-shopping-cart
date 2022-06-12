package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.exception.UnexpectedException;
import woowacourse.shoppingcart.exception.domain.CartItemNotFoundException;
import woowacourse.shoppingcart.exception.domain.ProductNotFoundException;

@Repository
public class CartItemDao {

    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(ProductDao productDao, JdbcTemplate jdbcTemplate) {
        this.productDao = productDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new CartItemNotFoundException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final Integer quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, quantity);
            return preparedStatement;
        }, keyHolder);
        return getInsertedOrUpdatedKey(keyHolder, customerId, productId);
    }

    private Long getInsertedOrUpdatedKey(KeyHolder keyHolder, Long customerId, Long productId) {
        final Number keyNumber = keyHolder.getKey();
        if (keyNumber != null) {
            return keyNumber.longValue();
        }
        return getUpdatedKey(customerId, productId)
            .orElseThrow(() -> new UnexpectedException("장바구니 품목 추가 중 알수 없는 오류가 발생했습니다."));
    }

    private Optional<Long> getUpdatedKey(Long customerId, Long productId) {
        final String query = "SELECT id FROM cart_item WHERE customer_id = ? and product_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, Long.class, customerId, productId));
    }

    public boolean deleteById(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        return DaoSupporter.isUpdated(jdbcTemplate.update(sql, id));
    }

    public Cart findCartByCustomerId(Long customerId) {
        final String query = "SELECT * FROM cart_item WHERE customer_id = ?";
        return new Cart(jdbcTemplate.query(query, getCartItemRowMapper(), customerId));
    }

    private RowMapper<CartItem> getCartItemRowMapper() {
        return (rs, rowNum) -> new CartItem(
            rs.getLong("id"),
            new Quantity(rs.getInt("quantity")),
            productDao.findProductById(rs.getLong("product_id"))
                .orElseThrow(ProductNotFoundException::new)
        );
    }

    public boolean updateQuantity(Long customerId, Long cartId, Quantity quantity) {
        final String query = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? AND id = ?";
        return DaoSupporter.isUpdated(jdbcTemplate.update(query, quantity.getAmount(), customerId, cartId));
    }

    public Optional<CartItem> findCartItemById(Long id) {
        final String query = "SELECT * FROM cart_item WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, getCartItemRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
