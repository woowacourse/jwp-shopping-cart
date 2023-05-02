package cart.member.dao;

import cart.member.domain.Member;
import cart.member.dto.MemberResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class MemberMemoryDao implements MemberDao {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Member> rowMapper;
    
    public MemberMemoryDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.simpleJdbcInsert = initSimpleJdbcInsert(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = initRowMapper();
    }
    
    private SimpleJdbcInsert initSimpleJdbcInsert(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }
    
    private RowMapper<Member> initRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
    
    @Override
    public Long save(final Member member) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", member.getEmail().getEmail())
                .addValue("password", member.getPassword().getPassword());
        
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }
    
    @Override
    public List<Member> findAll() {
        final String sql = "SELECT * FROM MEMBER";
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }
    
    @Override
    public Member findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT * FROM MEMBER WHERE email=:email AND password=:password";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password);
        
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
    }
    
    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM MEMBER";
        namedParameterJdbcTemplate.update(sql, Collections.emptyMap());
    }
}
