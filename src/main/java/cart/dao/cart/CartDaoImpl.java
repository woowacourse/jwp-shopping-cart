package cart.dao.cart;

import cart.entity.cart.Cart;
import cart.entity.member.Member;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CartDaoImpl implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Cart> rowMapper = (resultSet, rowNum) -> new Cart(
        resultSet.getLong("id"),
        resultSet.getLong("member_id"),
        resultSet.getLong("product_id"),
        resultSet.getInt("count")
    );

    public CartDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insertCart(final Cart cart) {
        String sql = "INSERT INTO cart(member_id, product_id, count) VALUES(?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cart.getMemberId());
            ps.setLong(2, cart.getProductId());
            ps.setInt(3, cart.getCount());
            return ps;
        }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("id").toString());
    }

    @Override
    public Optional<Cart> findByMemberIdAndProductId(final Member member, final Long productId) {
        String sql = "SELECT id, member_id, product_id, count FROM cart WHERE member_id = ? AND product_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, member.getId(), productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
