package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;


    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART").usingGeneratedKeyColumns("cart_id");
    }

    public long insert(CartEntity cartEntity) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("product_id", cartEntity.getProductId())
                .addValue("member_id", cartEntity.getMemberId());
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }
}
