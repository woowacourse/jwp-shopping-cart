package cart.member.dao;

import cart.member.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final RowMapper memberRowMapper = (resultSet, rowNum) -> {
        Member member = new Member(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return member;
    };

    public List<Member> selectAllMembers() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    public Member selectMemberByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM member WHERE email = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")), email, password);
    }
}
