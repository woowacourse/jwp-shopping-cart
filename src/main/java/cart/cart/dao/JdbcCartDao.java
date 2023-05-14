package cart.cart.dao;

import cart.cart.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCartDao implements CartDao {

    final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> memberRowMapper = (resultSet, rowNum) -> Cart.of(
            resultSet.getLong("id"),
            resultSet.getLong("member_id")
    );

    @Override
    public Cart findById(final Long memberId) {
        final String sql = "select c.id, c.member_id from cart as c join member as m on c.member_id = m.id where c.member_id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper, memberId);
    }
}
