package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

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

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final Integer quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public int deleteCartItems(final List<Long> ids) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        final String sql = "DELETE FROM cart_item WHERE id IN (:ids)";
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    public CartItem findByCustomerIdAndProductId(final Long customerId, final Long productId) {
        final String sql =
                "SELECT c.id as id, c.quantity as quantity, p.id as product_id, p.name as name, p.price as price, p.image_url as image_url"
                        + " FROM cart_item c INNER JOIN product p ON c.product_id = p.id WHERE c.customer_id = ? and c.product_id = ?";
        return jdbcTemplate.queryForObject(sql, getRowMapper(), customerId, productId);
    }

    private RowMapper<CartItem> getRowMapper() {
        return (res, row) ->
                new CartItem(
                        res.getLong("id"),
                        res.getLong("product_id"),
                        res.getString("name"),
                        res.getInt("price"),
                        res.getString("image_url"),
                        res.getInt("quantity")
                );
    }

    public int update(final CartItem cartItem) {
        final String cartItemSql = "UPDATE cart_item SET quantity = ? where id = ?";
        final String productSql = "UPDATE product SET name = ?, price = ?, image_url = ? where id = ?";
        final int cartItemAffectedRows = jdbcTemplate.update(cartItemSql, cartItem.getQuantity(), cartItem.getId());
        final int productAffectedRows = jdbcTemplate.update(productSql,
                cartItem.getName(), cartItem.getPrice(), cartItem.getImageUrl(), cartItem.getProductId());
        return cartItemAffectedRows + productAffectedRows;
    }
}
