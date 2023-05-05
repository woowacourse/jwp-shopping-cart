package cart.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.entity.Member;
import cart.entity.MemberRepository;

@Repository
public class DBMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Member member) {
        String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setString(2, member.getPassword());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Member findById(Long id) {
        String sql = "SELECT email, password FROM member WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) -> Member.of(
                id,
                resultSet.getString("email"),
                resultSet.getString("password")),
            id);
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql,
            (resultSet, rowNum) -> Member.of(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")));
    }

    @Override
    public void updateById(Member member, Long id) {
        String sql = "UPDATE member SET email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            member.getEmail(),
            member.getPassword(),
            id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
