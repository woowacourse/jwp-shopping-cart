package cart.repository.cart.dao;

import cart.entiy.cart.CartEntity;
import cart.entiy.user.UserEntityId;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class CartDao {

    private static final RowMapper<CartEntity> rowMapper = (rs, rowNum) -> new CartEntity(
            rs.getLong("cart_id"),
            rs.getLong("member_id")
    );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("cart_id");
    }

    public CartEntity save(final CartEntity cartEntity) {
        final Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("cart_id", cartEntity.getCartId().getValue());
        parameters.put("member_id", cartEntity.getUserEntityId().getValue());
        final long cartId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new CartEntity(cartId, cartEntity);
    }

    public CartEntity findByUserId(final UserEntityId userEntityId) {
        return jdbcTemplate.queryForObject(
                "SELECT cart_id,member_id FROM cart WHERE member_id = ?",
                rowMapper,
                userEntityId.getValue()
        );
    }
}
