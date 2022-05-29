package woowacourse.member.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.member.domain.Member;

@Repository
public class MemberDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Member member) {
        SqlParameterSource parameters = new MapSqlParameterSource("email", member.getEmail())
                .addValue("password", member.getPassword())
                .addValue("name", member.getName());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public boolean isEmailExist(final String email) {
        String sql = "SELECT EXISTS(SELECT id FROM member WHERE email = :email)";
        SqlParameterSource parameters = new MapSqlParameterSource("email", email);

        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, parameters, Boolean.class));
    }

    public Member findByEmail(final String email) {
        String sql= "SELECT id, email, password, name FROM member WHERE email = :email";
        SqlParameterSource parameters = new MapSqlParameterSource("email", email);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, rowMapper());
    }

    private RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            return new Member(id, email, password, name);
        };
    }
}
