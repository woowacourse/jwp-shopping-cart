package cart.dao;

import cart.domain.member.Member;
import cart.domain.member.MemberDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2MemberDao implements MemberDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Member> rowMapper = (rs, rowNum) ->
            new Member(rs.getLong("id"), rs.getString("email"), rs.getString("password"));


    @Override
    public Member save(Member member) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);
        long memberId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Member(memberId, member.getEmail(), member.getPassword());
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        return jdbcTemplate.query(sql, rowMapper, email).stream()
                .findAny();
    }
}
