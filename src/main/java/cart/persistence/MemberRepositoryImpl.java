package cart.persistence;

import cart.domain.Member;
import cart.domain.MemberRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Long memberId = rs.getLong("member_id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        return new Member(memberId, email, password);
    };

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member(email, password) VALUES(?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((con -> {
            var psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, member.getEmail());
            psmt.setString(2, member.getPassword());
            return psmt;
        }), keyHolder);

        Long memberId = keyHolder.getKey().longValue();

        return new Member(memberId, member.getEmail(), member.getPassword());
    }

    @Override
    public Member findById(Long id) {
        String sql = "SELECT member_id, email, password FROM member WHERE member_id = ?";


        return jdbcTemplate.queryForObject(sql, memberRowMapper);
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT member_id, email, password FROM member";

        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "SELECT member_id, email, password FROM member WHERE email = ?";

        return jdbcTemplate.queryForObject(sql, memberRowMapper, email);
    }
}
