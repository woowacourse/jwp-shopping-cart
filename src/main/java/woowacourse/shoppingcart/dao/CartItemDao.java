package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByMemberId(final Long memberId) {
        final String sql = "SELECT product_id FROM cart_item WHERE member_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), memberId);
    }

    public List<Long> findIdsByMemberId(final Long memberId) {
        final String sql = "SELECT id FROM cart_item WHERE member_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), memberId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
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

    public boolean isExistCartItem(final Long memberId, final Long productId) {
        try {
            String sql = "SELECT COUNT(*) FROM cart_item WHERE member_id = ? AND product_id = ?";
            return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Integer.class, memberId, productId))
                    .compareTo(0) > 0;
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Integer findProductQuantityIdById(final Long cartId) {
        try {
            final String sql = "SELECT quantity FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getInt("quantity"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
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
        return keyHolder.getKey().longValue();
    }

    public void addOneQuantityCartItem(final Long memberId, final Long productId) {
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
