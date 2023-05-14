package cart.dao;

import cart.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberDao {

    private JdbcTemplate template;
    private SimpleJdbcInsert simpleJdbcInsert;
    private RowMapper<Member> rowMapper = ((rs, rowNum) ->
            new Member(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
            ));

    public MemberDao(final JdbcTemplate template) {
        this.template = template;
        this.simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Member member) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(member);
        return simpleJdbcInsert.executeAndReturnKey(source).longValue();
    }

    public List<Member> findAll() {
        final String sql = "select * from member";
        return template.query(sql, rowMapper);
    }

    public Member findByEmail(String email){
        final String sql = "select * from member where email = ?";
        return template.query(sql,rowMapper,email).get(0);
    }

    public void deleteById(Long id) {
        final String sql = "delete from member where id = ?";
        template.update(sql, id);
    }
}
