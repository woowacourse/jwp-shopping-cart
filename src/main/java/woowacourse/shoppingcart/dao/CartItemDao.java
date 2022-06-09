package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<CartItem> findCartItemByCustomerIdAndProductId(final Long customerId, final Long productId) {
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
                "WHERE customer_id = ? AND product_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                final Product product = new Product(
                        rs.getLong("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("thumbnail_url"),
                        rs.getInt("quantity")
                );
                return new CartItem(rs.getLong("id"), product, rs.getInt("count"));
            }, customerId, productId));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
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
            throw new NotFoundProductException();
        }
    }

    public void updateCartItem(final Long customerId, final Long productId, final Integer count) {
        final String sql = "UPDATE cart_item SET count = ? WHERE customer_id = ? AND product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, count, customerId, productId);
        if (rowCount == 0) {
            throw new NotFoundProductException();
        }
    }
}
