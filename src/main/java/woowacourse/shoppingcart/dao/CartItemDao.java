package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId){
        final String sql = "SELECT " +
                "product.id as product_id, " +
                "product.name as product_name, " +
                "product.price as price, " +
                "product.thumbnail_url as thumbnail_url, " +
                "product.quantity as quantity, " +
                "cart_item.id as id, " +
                "cart_item.count as count " +
                "FROM cart_item " +
                "INNER JOIN product ON cart_item.product_id = product.id " +
                "WHERE customer_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            final Product product = new Product(
                    rs.getLong("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("price"),
                    rs.getString("thumbnail_url"),
                    rs.getInt("quantity")
            );

            return new CartItem(rs.getLong("id"), product, rs.getInt("count"));
        }, customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final Integer count) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, count);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? AND product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
