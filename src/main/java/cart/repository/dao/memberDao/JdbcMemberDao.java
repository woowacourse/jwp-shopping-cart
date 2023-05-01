package cart.repository.dao.memberDao;

import cart.entity.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcMemberDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Long> save(final Member member) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(member);
        try {
            return Optional.of((Long) simpleJdbcInsert.executeAndReturnKey(source));
        } catch (final DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findById(final Long id) {
        final String sql = "select * from member where id = :id";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            final Member member = template.queryForObject(sql, source, rowMapper());
            return Optional.of(member);
        } catch (final DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        final String sql = "select * from member where email = :email";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("email", email);
        try {
            final Member member = template.queryForObject(sql, source, rowMapper());
            return Optional.of(member);
        } catch (final DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        final String sql = "select * from member";
        return template.query(sql, rowMapper());
    }

    public RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> {
            final Long id = rs.getLong("id");
            final String email = rs.getString("email");
            final String name = rs.getString("name");
            final String password = rs.getString("password");
            return new Member(id, email, name, password);
        };
    }
}
