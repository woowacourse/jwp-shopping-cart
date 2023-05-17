package cart.auth;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final JdbcTemplate jdbcTemplate;

    public AuthService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkInvalidLogin(String principal, String credentials) {
        String sql = "SELECT * FROM MEMBER WHERE email = ? AND password = ?";

        List<MemberEntity> members = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    return new MemberEntity(id, email, password);
                }, principal, credentials);

        if (members.size() < 0) {
            return true;
        }

        return false;
    }
}
