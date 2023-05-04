package cart.repository.dao;

import cart.repository.entity.CartEntity;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartEntity> actorRowMapper = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong("cart_id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final CartEntity cartEntity) {
        final String sql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, cartEntity.getMemberId());
            preparedStatement.setLong(2, cartEntity.getProductId());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<CartEntity> findByMemberId(final Long memberId) {
        final String sql = "SELECT cart_id, member_id, product_id FROM cart WHERE member_id = ?";
        return jdbcTemplate.query(sql, actorRowMapper, memberId);
    }

    @Override
    public int delete(final Long memberId, final Long productId) {
        final String sql = "DELETE FROM cart WHERE member_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, memberId, productId);
    }
}
