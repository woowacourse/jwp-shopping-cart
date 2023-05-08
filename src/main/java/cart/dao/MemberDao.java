package cart.dao;

import cart.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "SELECT * FROM member";
        BeanPropertyRowMapper<MemberEntity> mapper = BeanPropertyRowMapper.newInstance(MemberEntity.class);
        return jdbcTemplate.query(sql, mapper);
    }

    public Optional<MemberEntity> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
        BeanPropertyRowMapper<MemberEntity> mapper = BeanPropertyRowMapper.newInstance(MemberEntity.class);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapper, email, password));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
