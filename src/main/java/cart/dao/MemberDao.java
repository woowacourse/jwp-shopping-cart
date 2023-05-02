package cart.dao;

import cart.dto.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MemberEntity save(final MemberEntity member) {
        String sql = "INSERT INTO members (email, password) VALUES (?,?)";

        final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setString(2, member.getPassword());
            return preparedStatement;
        }, generatedKeyHolder);

        member.setId(generatedKeyHolder.getKey().longValue());
        return member;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new MemberEntity(rs.getLong(1), rs.getString("email"), rs.getString("password")));
    }

    public MemberEntity findByEmail(String memberEmail) {
        String sql = "SELECT * FROM members WHERE email LIKE ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new MemberEntity(rs.getLong("id"), rs.getString("email"), rs.getString("password")), memberEmail);
    }
}
