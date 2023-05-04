package cart.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CartUserDao {

    private final JdbcTemplate jdbcTemplate;

    public CartUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
