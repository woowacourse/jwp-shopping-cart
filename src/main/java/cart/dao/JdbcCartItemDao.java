package cart.dao;

import cart.domain.entity.CartItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCartItemDao implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CartItem> cartItemEntityRowMapper = (resultSet, rowNum) -> CartItem.of(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );

    @Override
    public List<CartItem> selectAllByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM cart_item WHERE member_id = ?";
        return jdbcTemplate.query(sql, cartItemEntityRowMapper, memberId);
    }

    @Override
    public long insert(final CartItem cartItem) {
        final String sql = "INSERT INTO cart_item(member_id, product_id) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, cartItem.getMemberId(), cartItem.getProductId());
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException("장바구니에 담을 수 없습니다");
        }
    }

    @Override
    public int deleteById(final long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
