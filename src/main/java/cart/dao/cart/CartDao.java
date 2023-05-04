package cart.dao.cart;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDao {
    private static final RowMapper<CartEntity> rowMapper =
            (rs, rowNum) -> new CartEntity(
                    rs.getLong("cart_id"),
                    rs.getLong("product_id"),
                    rs.getLong("user_id")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("cart_id");
    }

    public Long insert(CartEntity cartEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(cartEntity);

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public List<CartEntity> findAll() {
        String findAllQuery = "SELECT * FROM cart";

        return jdbcTemplate.query(findAllQuery, rowMapper);
    }

    public List<CartEntity> findByUserId(Long userId) {
        String findByUserIdQuery = "SELECT * FROM cart WHERE user_id = ?";

        return jdbcTemplate.query(findByUserIdQuery, rowMapper, userId);
    }

    public int deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM cart WHERE cart_id = ?";

        return jdbcTemplate.update(deleteByIdQuery, id);
    }
}
