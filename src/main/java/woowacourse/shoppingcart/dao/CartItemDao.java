package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cart> findCartsByMemberId(final Long memberId) {
        final String sql = "SELECT ci.id, ci.product_id, p.name, p.price, p.image_url, ci.quantity "
                + "FROM cart_item AS ci "
                + "JOIN product AS p "
                + "WHERE ci.product_id = p.id AND ci.member_id = ?";

        return jdbcTemplate.query(sql, (rs, count) -> new Cart(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url"),
                rs.getInt("quantity")
        ), memberId);
    }

    public Cart findCartByMemberId(final Long cartId) {
        try {
            final String sql = "SELECT ci.id, ci.product_id, p.name, p.price, p.image_url, ci.quantity "
                    + "FROM cart_item AS ci "
                    + "JOIN product AS p "
                    + "WHERE ci.product_id = p.id AND ci.id = ?";

            return jdbcTemplate.queryForObject(sql, (rs, count) -> new Cart(
                    rs.getLong("id"),
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url"),
                    rs.getInt("quantity")
            ), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<Long> findIdsByMemberId(final Long memberId) {
        final String sql = "SELECT id FROM cart_item WHERE member_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), memberId);
    }

    public Long findIdByMemberIdAndProductId(final Long memberId, final Long productId) {
        try {
            final String sql = "SELECT id FROM cart_item WHERE member_id = ? AND product_id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("id"),
                    memberId, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public boolean isValidCartItem(final Long memberId, final Long productId) {
        final String sql = "SELECT EXISTS(SELECT * FROM cart_item WHERE member_id = ? AND product_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, memberId, productId));
    }

    public Long addCartItem(final Long memberId, final Long productId) {
        final String sql = "INSERT INTO cart_item(member_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, memberId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, 1);
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void addQuantityCartItem(final Long memberId, final Long productId) {
        final String sql = "UPDATE cart_item SET quantity = quantity+1 WHERE member_id = ? AND product_id = ?";

        jdbcTemplate.update(sql, memberId, productId);
    }

    public void updateCartItemQuantity(final Long cartId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        jdbcTemplate.update(sql, quantity, cartId);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
