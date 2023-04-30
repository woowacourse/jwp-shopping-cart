package cart.dao;

import cart.controller.dto.MemberRequest;
import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MySQLMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(MemberRequest request) {
        String query = "INSERT INTO member (email, password) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, request.getEmail());
            ps.setString(2, request.getPassword());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<MemberEntity> findAll() {
        String query = "SELECT * FROM member";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")));
    }

    @Override
    public long findIdByMember(Member member) {
        String query = "SELECT id FROM member WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(query,
            (resultSet, rowNum) -> resultSet.getLong("id"),
            member.getEmail(), member.getPassword());

    }
}
