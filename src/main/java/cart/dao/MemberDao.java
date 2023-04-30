package cart.dao;

import java.sql.PreparedStatement;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(MemberEntity memberEntity) {
        String sql = "INSERT INTO MEMBER (nickname, email, password) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"member_id"});
            ps.setString(1, memberEntity.getNickname());
            ps.setString(2, memberEntity.getEmail());
            ps.setString(3, memberEntity.getPassword());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
