package woowacourse.member.dao;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.member.domain.Member;

@Repository
public class MemberDao {

    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Member member) {
        SqlParameterSource parameters = new MapSqlParameterSource("email", member.getEmail())
                .addValue("password", member.getPassword())
                .addValue("name", member.getName());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }
}
