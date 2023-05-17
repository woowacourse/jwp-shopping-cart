package cart.dao;

import cart.domain.MemberEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM MEMBERS";
        return jdbcTemplate.query(sql, getMemberEntityRowMapper());
    }

    private static RowMapper<MemberEntity> getMemberEntityRowMapper() {
        return (rs, rowNum) -> new MemberEntity.Builder()
                .id(rs.getInt("id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build();
    }

    public Optional<MemberEntity> findBy(String email) {
        String sql = "SELECT * FROM MEMBERS WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getMemberEntityRowMapper(), email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
