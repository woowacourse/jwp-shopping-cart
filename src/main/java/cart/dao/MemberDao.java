package cart.dao;

import cart.dao.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cart.dao.ObjectMapper.getMemberRowMapper;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String query = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(query, getMemberRowMapper());
    }

    public Long findIdByAuthInfo(final String email, final String password) {
        final String query = "SELECT id FROM MEMBER WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(query, Long.class, email, password);
    }
}
