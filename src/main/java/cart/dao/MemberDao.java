package cart.dao;

import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MemberEntity> memberRowMapper = (resultSet, rowNum) -> {
        MemberEntity memberEntity = new MemberEntity(
                resultSet.getLong("member_id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("name")
        );
        return memberEntity;
    };

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Member member) {
        String sql = "INSERT INTO MEMBER(email, password, name) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"member_id"});
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<MemberEntity> findById(Long id) {
        String sql = "SELECT member_id, email, password, name FROM MEMBER WHERE member_id = ?";

        return jdbcTemplate.query(sql, memberRowMapper, id)
                .stream()
                .findAny();
    }

    public Optional<MemberEntity> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT member_id, email, password, name FROM MEMBER WHERE email = ? and password = ?";

        return jdbcTemplate.query(sql, memberRowMapper, email, password)
                .stream()
                .findAny();
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT member_id, email, password, name FROM MEMBER";

        return jdbcTemplate.query(sql, memberRowMapper);
    }
}
