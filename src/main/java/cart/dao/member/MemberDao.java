package cart.dao.member;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {

    private static final RowMapper<MemberEntity> memeberRowMapper = (rs, rowNum) -> new MemberEntity(
            rs.getLong("member_id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(MemberEntity memberEntity) {
        String sql = "insert into MEMBER(name, email, password) values (:name,:email,:password)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", memberEntity.getName())
                .addValue("email", memberEntity.getEmail())
                .addValue("password", memberEntity.getPassword());

        jdbcTemplate.update(sql, paramSource, keyHolder);

        return keyHolder.getKey().longValue();

    }


    public List<MemberEntity> findAll() {
        String sql = "select * from MEMBER";
        return jdbcTemplate.query(sql, memeberRowMapper);

    }

    public Optional<MemberEntity> findById(Long id) {
        String sql = "select * from MEMBER where member_id = :member_id";

        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("member_id", id);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, paramSource, memeberRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }

    }

    public void update(MemberEntity memberEntity) {
        String sql = "update MEMBER set name = :name, email = :email, password = :password where member_id = :member_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("member_id", memberEntity.getId())
                .addValue("name", memberEntity.getName())
                .addValue("email", memberEntity.getEmail())
                .addValue("password", memberEntity.getPassword());

        jdbcTemplate.update(sql, paramSource);

    }

    public void delete(Long memberId) {
        String sql = "delete from MEMBER where member_id = :member_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("member_id", memberId);

        jdbcTemplate.update(sql, paramSource);

    }
}
