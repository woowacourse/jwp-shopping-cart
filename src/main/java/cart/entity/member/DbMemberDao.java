package cart.entity.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DbMemberDao implements MemberDao {

    public static final RowMapper<Member> memberRowMapper = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public DbMemberDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingColumns("email", "password")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Member save(final Member member) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);
        final long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Member(id, member.getEmail(), member.getPassword());
    }

    @Override
    public List<Member> findAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return findAll().stream().filter(member -> member.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<Member> findByEmailAndPassword(final String email, final String password) {
        final Optional<Member> foundMember = findByEmail(email);
        if (foundMember.isEmpty()) {
            return Optional.empty();
        }
        final String sql = "SELECT * FROM member WHERE email = :email";
        final Map<String, String> parameters = Collections.singletonMap("email", email);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameters, memberRowMapper));
    }
}
