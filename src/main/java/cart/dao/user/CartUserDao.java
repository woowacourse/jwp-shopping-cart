package cart.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartUserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public CartUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_user")
                .usingGeneratedKeyColumns("cart_user_id");
    }

    public CartUserEntity findByEmail(String email) {
        String findByEmailQuery = "SELECT * FROM cart_user WHERE email = ?";

        return jdbcTemplate.queryForObject(findByEmailQuery, (rs, rowNum) -> {
            long cartUserId = rs.getLong("cart_user_id");
            String cartUserEmail = rs.getString("email");
            String cartUserPassword = rs.getString("cart_password");
            return new CartUserEntity(cartUserId, cartUserEmail, cartUserPassword);
        }, email);
    }

    public Long insert(CartUserEntity cartUserEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(cartUserEntity);

        return simpleInsert.executeAndReturnKey(params).longValue();
    }
}
