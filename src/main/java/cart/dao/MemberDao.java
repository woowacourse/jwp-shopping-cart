package cart.dao;

import cart.domain.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public MemberEntity findBy(String email) {
        String sql = "SELECT * FROM MEMBERS WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, getMemberEntityRowMapper(), email);
    }

}
