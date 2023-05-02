package cart.repository;

import cart.entity.Member;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        return new Member(id, email, password);
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MemberRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Member> findAll() {
        final String sql = "select * from member";
        return namedParameterJdbcTemplate.query(sql, MEMBER_ROW_MAPPER);
    }
}
