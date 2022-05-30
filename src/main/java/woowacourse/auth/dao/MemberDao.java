package woowacourse.auth.dao;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.Member;

@Repository
public class MemberDao {

    private static final RowMapper<Member> MEMBER_MAPPER = ((rs, rowNum) -> new Member(
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

    public void save(Member member) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(member);
        simpleJdbcInsert.execute(params);
    }

    public boolean existsEmail(String email) {
        String sql = "SELECT EXISTS (SELECT 1 FROM MEMBER WHERE email = :email)";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT email, password, nickname FROM MEMBER WHERE email = :email";
        SqlParameterSource params = new MapSqlParameterSource("email", email);
        return namedParameterJdbcTemplate.query(sql, params, MEMBER_MAPPER)
                .stream()
                .findAny();
    }

    public void updateNicknameByEmail(String email, String nickname) {
        String sql = "UPDATE MEMBER SET nickname = :nickname WHERE email = :email";
        SqlParameterSource parameters = new MapSqlParameterSource("email", email)
                .addValue("nickname", nickname);
        namedParameterJdbcTemplate.update(sql, parameters);
    }


    public void updatePasswordByEmail(String email, String password) {
        String sql = "UPDATE MEMBER SET password = :password WHERE email = :email";
        SqlParameterSource parameters = new MapSqlParameterSource("email", email)
                .addValue("password", password);
        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
