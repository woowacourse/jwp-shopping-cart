package cart.repository.member;

import cart.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemberDbRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDbRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, email, password FROM member";

        return namedParameterJdbcTemplate.query(sql, getMemberRowMapper());
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        String sql = "SELECT id, email, password FROM member WHERE email = :email";

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource("email", email), getMemberRowMapper()).stream()
                .findAny();
    }

    @Override
    public void save(final Member member) {
        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(member);
        KeyHolder keyHolder = simpleJdbcInsert.executeAndReturnKeyHolder(source);
        Long generatedId = keyHolder.getKeyAs(Long.class);
        member.setId(generatedId);
    }

    private RowMapper<Member> getMemberRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String email = rs.getString("email");
            String password = rs.getString("password");

            return Member.from(id, email, password);
        };
    }
}
