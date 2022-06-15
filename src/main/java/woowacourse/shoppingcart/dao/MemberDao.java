package woowacourse.shoppingcart.dao;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Member;

@Repository
public class MemberDao {

    private static final RowMapper<Member> MEMBER_MAPPER = ((rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("nickname")
    ));

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Long save(Member member) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(member);
        return simpleJdbcInsert.executeAndReturnKey(params)
                .longValue();
    }

    public boolean existsEmail(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM member WHERE email = :email)";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public boolean exists(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM member WHERE id = :id)";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT id, email, password, nickname FROM member WHERE email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.query(sql, params, MEMBER_MAPPER)
                .stream()
                .findAny();
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, email, password, nickname FROM member WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(sql, params, MEMBER_MAPPER)
                .stream()
                .findAny();
    }

    public void updateNicknameByEmail(String email, String nickname) {
        String sql = "UPDATE member SET nickname = :nickname WHERE email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email)
                .addValue("nickname", nickname);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updateNicknameById(Long id, String nickname) {
        String sql = "UPDATE member SET nickname = :nickname WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id)
                .addValue("nickname", nickname);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updatePasswordByEmail(String email, String password) {
        String sql = "UPDATE member SET password = :password WHERE email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email)
                .addValue("password", password);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updatePasswordById(Long id, String password) {
        String sql = "UPDATE member SET password = :password WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id)
                .addValue("password", password);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteByEmail(String email) {
        String sql = "DELETE FROM member WHERE email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
