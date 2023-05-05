package cart.dao;

import cart.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Member member) {
        String sql = "insert into MEMBER (email, password) values (?,?)";

        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    public List<Member> findAll() {
        String sql = "select * from MEMBER";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Member(rs.getLong("member_id"),
                rs.getString("email"),
                rs.getString("password")));
    }

    public Long findByEmailAndPassword(String email, String password) {
        String sql = "select member_id from MEMBER where email = (?) AND password = (?)";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Long(rs.getLong("member_id")), email, password);
    }
}
