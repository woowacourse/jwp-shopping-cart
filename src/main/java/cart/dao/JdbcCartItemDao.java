package cart.dao;

import cart.entity.CartItemEntity;
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

    private final RowMapper<CartItemEntity> cartItemEntityRowMapper = (resultSet, rowNum) -> CartItemEntity.of(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );

    @Override
    public List<CartItemEntity> selectAllByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM cart_item WHERE member_id = ?";
        return jdbcTemplate.query(sql, cartItemEntityRowMapper, memberId);
    }

    @Override
    public long insert(final CartItemEntity cartItemEntity) {
        final String sql = "INSERT INTO cart_item(member_id, product_id) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, cartItemEntity.getMemberId(), cartItemEntity.getProductId());
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    @Override
    public int deleteById(final long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
