package cart.dao.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.Quantity;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNumber) -> {
        Long productId = resultSet.getLong("product_id");
        Long memberId = resultSet.getLong("member_id");
        int quantity = resultSet.getInt("quantity");

        return new Cart(productId, memberId, new Quantity(quantity));
    };

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(final Cart cart) {
        final String sql = "INSERT INTO cart(product_id, member_id) VALUES(?, ?)";
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cart.getProductId());
            preparedStatement.setLong(2, cart.getMemberId());
            return preparedStatement;
        });
    }

    @Override
    public List<Cart> findAll() {
        final String sql = "select * from cart";
        return jdbcTemplate.query(sql, cartRowMapper);
    }

    @Override
    public Optional<Cart> findByMemberIdAndProductId(final Long memberId, final Long productId) {
        final String sql = "select * from cart where member_id = ? and product_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, cartRowMapper, memberId, productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByMemberIdAndProductId(final Long memberId, final Long productId) {
        final String sql = "delete from cart where member_id = ? and product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }

    @Override
    public void update(final Cart cart) {
        final String sql = "update cart set quantity = ? where product_id = ? and member_id = ?";
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cart.getQuantity().getValue());
            preparedStatement.setLong(2, cart.getProductId());
            preparedStatement.setLong(3, cart.getMemberId());
            return preparedStatement;
        });
    }
}
