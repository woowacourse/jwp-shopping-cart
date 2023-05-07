package cart.dao;

import cart.dto.entity.MemberEntity;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String sql = "SELECT * FROM member";
        BeanPropertyRowMapper<MemberEntity> mapper = BeanPropertyRowMapper.newInstance(MemberEntity.class);
        return jdbcTemplate.query(sql, mapper);
    }

    public List<MemberEntity> findByEmailWithPassword(String email, String password) {
        final String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
        BeanPropertyRowMapper<MemberEntity> mapper = BeanPropertyRowMapper.newInstance(MemberEntity.class);
        return jdbcTemplate.query(sql, mapper, email, password);
    }
}
