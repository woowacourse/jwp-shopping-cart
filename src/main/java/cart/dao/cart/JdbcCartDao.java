package cart.dao.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.Quantity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNumber) -> {
        Long productId = resultSet.getLong("product_id");
        Long memberId = resultSet.getLong("member_id");
        int quantity = resultSet.getInt("quantity");

        return new Cart(productId, memberId, new Quantity(quantity));
    };

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void insert(final Cart cart) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("product_id", cart.getProductId());
        parameters.put("member_id", cart.getMemberId());

        simpleJdbcInsert.execute(parameters);
    }

    @Override
    public List<Cart> findAll() {
        final String sql = "select product_id, member_id, COUNT(id) as quantity from cart group by product_id, member_id";
        return jdbcTemplate.query(sql, cartRowMapper);
    }

    @Override
    public Optional<Cart> findByMemberIdAndProductId(final Long memberId, final Long productId) {
        final String sql = "select product_id, member_id, COUNT(id) as quantity from cart group by product_id, member_id "
                + "having member_id = ? and product_id = ?";
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
}
