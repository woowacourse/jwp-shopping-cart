package cart.dao;

import cart.entity.MemberEntity;
import cart.exception.MemberNotFoundException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;
    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public MemberEntity findByCredentials(final String email, final String password) {
        String sql = "SELECT id, email, password FROM member WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, memberRowMapper(), email, password);
        } catch (EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("존재하지 않는 사용자입니다.");
        }
    }

    public boolean existsMemberByCredentials(final String email, final String password) {
        String sql = "SELECT COUNT(*) FROM member WHERE email = ? AND password = ?";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class, email, password);

        if (integer == null) {
            return false;
        }

        return integer > 0;
    }

    private RowMapper<MemberEntity> memberRowMapper() {
        return (rs, rowNum) -> MemberEntity.builder()
                .id(rs.getLong(1))
                .email(rs.getString(2))
                .password(rs.getString(3))
                .build();
    }
}
