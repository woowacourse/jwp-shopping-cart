package cart.dao;

import java.util.List;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("MEMBER").usingGeneratedKeyColumns("member_id");
    }

    public long insert(MemberEntity memberEntity) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nickname", memberEntity.getNickname())
                .addValue("email", memberEntity.getEmail())
                .addValue("password", memberEntity.getPassword());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<MemberEntity> selectAll() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    private RowMapper<MemberEntity> memberRowMapper() {
        return ((rs, rowNum) ->
                new MemberEntity.Builder()
                        .memberId(rs.getLong("member_id"))
                        .nickname(rs.getString("nickname"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build()
        );
    }

    public Boolean isExistByNickname(String nickname) {
        String sql = "SELECT EXISTS(SELECT 1 FROM MEMBER WHERE nickname = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, nickname);
    }

    public Boolean isExistByEmail(String email) {
        String sql = "SELECT EXISTS(SELECT 1 FROM MEMBER WHERE email = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    public Boolean isNotExistByEmailAndPassword(String email, String password) {
        String sql = "SELECT EXISTS(SELECT 1 FROM MEMBER WHERE email = ? and password = ?)";
        return Boolean.FALSE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, email, password));
    }
}
