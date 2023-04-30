package cart.dao;

import cart.entity.Member;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class H2MemberDao implements MemberDao {

    public static final RowMapper<Member> memberRowMapper = (resultSet, rowMapper) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member (email, password) VALUES (:email, :password)";

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Member(id, member.getEmail(), member.getPassword());
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = :id";
        Map<String, Long> parameter = Collections.singletonMap("id", id);
        return findMember(sql, parameter);
    }

    private Optional<Member> findMember(String sql, Map<String, Long> parameter) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameter, memberRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
