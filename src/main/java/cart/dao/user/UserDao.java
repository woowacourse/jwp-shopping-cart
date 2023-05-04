package cart.dao.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserEntity findByEmail(String email) {
        String findByEmailQuery = "SELECT * FROM cart_user WHERE email = ?";

        return jdbcTemplate.queryForObject(findByEmailQuery, (rs, rowNum) -> {
            long cartUserId = rs.getLong("cart_user_id");
            String cartUserEmail = rs.getString("email");
            String cartUserPassword = rs.getString("cart_password");
            return new UserEntity(cartUserId, cartUserEmail, cartUserPassword);
        }, email);
    }
}
