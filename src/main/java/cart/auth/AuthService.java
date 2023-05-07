package cart.auth;

import cart.entity.Member;
import cart.presentation.dto.MemberResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private static String EMAIL;
    private static String PASSWORD;
    private final JdbcTemplate jdbcTemplate;

    public AuthService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkInvalidLogin(String principal, String credentials) {
        String sql = "SELECT * FROM MEMBER WHERE email = ? AND password = ?";

        List<Member> members = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    return new Member(id, email, password);
                }, principal, credentials);

        if (members.size() < 0) {
            return true;
        }

        return false;
    }

    public MemberResponse findMember(String principal) {
        return new MemberResponse(1, principal, PASSWORD);
    }

}
