package cart.dao;

import cart.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {

    private static final RowMapper<Member> memberRowMapper =
            (rs, rowNum) -> new Member(rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3));

    private final JdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(final JdbcTemplate template) {
        this.template = template;
        this.simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }


    public Long save(final Member member) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(member);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public Optional<Member> findByEmail(final String email) {
        final String sql = "select * from member where email = ?";
        try {
            return Optional.ofNullable(template.queryForObject(sql, memberRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Member> findAll() {
        final String sql = "select * from member";
        return template.query(sql, memberRowMapper);
    }
}
