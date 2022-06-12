package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public void updateCartItem(final Long customerId, final Long productId, final Integer count) {
        final String sql = "UPDATE cart_item SET count = ? WHERE customer_id = ? AND product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, count, customerId, productId);
        if (rowCount == 0) {
            throw new NotFoundProductException();
        }
    }

    public List<CartItem> findCartItemsByProductIdsAndCustomerId(final List<Long> productIds, final Long customerId) {
        return findCartItemsByCustomerId(customerId)
                .stream()
                .filter(cartItem -> productIds.contains(cartItem.getProductId()))
                .collect(Collectors.toList());
    }

    public void updateCartItemCount(final Long productId, final Integer quantity) {
        final String sql = "UPDATE cart_item SET count = ? WHERE count > ? AND product_id = ?";

        jdbcTemplate.update(sql, quantity, quantity, productId);
    }

    public int deleteCartItems(final List<Long> productIds, final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE product_id = ? AND customer_id = ?";

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, productIds.get(i));
                ps.setLong(2, customerId);
            }

            @Override
            public int getBatchSize() {
                return productIds.size();
            }
        }).length;
    }
}
