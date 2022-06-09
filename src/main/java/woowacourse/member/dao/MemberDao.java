package woowacourse.member.dao;

import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.member.domain.Member;

@Repository
public class MemberDao {

    private static final RowMapper<Member> MEMBER_MAPPER = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("nickname")
    );

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("MEMBER")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void save(Member member) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(member);
        simpleJdbcInsert.execute(params);
    }

    public boolean existsEmail(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM MEMBER WHERE email = :email)";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public boolean checkIdExistence(long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM MEMBER WHERE id = :id)";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public Member findById(long id) {
        String sql = "SELECT id, email, password, nickname FROM MEMBER WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, MEMBER_MAPPER);
    }

    public Optional<Member> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, email, password, nickname FROM MEMBER WHERE email = :email AND password = :password";
        SqlParameterSource params = new MapSqlParameterSource("email", email)
                .addValue("password", password);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, MEMBER_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean checkPassword(long id, String password) {
        String sql = "SELECT EXISTS (SELECT 1 FROM MEMBER WHERE id = :id AND password = :password)";
        SqlParameterSource params = new MapSqlParameterSource("id", id)
                .addValue("password", password);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public void updateNicknameById(long id, String nickname) {
        String sql = "UPDATE MEMBER SET nickname = :nickname WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id)
                .addValue("nickname", nickname);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updatePasswordById(long id, String password) {
        String sql = "UPDATE MEMBER SET password = :password WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id)
                .addValue("password", password);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM MEMBER WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
