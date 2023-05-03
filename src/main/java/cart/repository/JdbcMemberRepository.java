package cart.repository;

import cart.entity.Member;
import cart.service.dto.MemberInfo;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        return new Member(id, email, password);
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcMemberRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Member> findAll() {
        final String sql = "select * from member";
        return namedParameterJdbcTemplate.query(sql, MEMBER_ROW_MAPPER);
    }

    @Override
    public Optional<Long> findId(final MemberInfo memberInfo) {
        final String sql = "select id from member where email=:email and password=:password";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(memberInfo);
        final Long memberId = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Long.class);
        return Optional.ofNullable(memberId);
    }
}
