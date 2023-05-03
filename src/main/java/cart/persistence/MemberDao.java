package cart.persistence;

import cart.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer insert(Member member) {
        java.lang.String sql = "INSERT into MEMBER (email, password) values(?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new java.lang.String[]{"id"});
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM MEMBER";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    return new Member(id, email, password);
                });
    }

    public Integer update(Integer id, Member member) {
        java.lang.String sql = "UPDATE MEMBER SET email = ?, password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, member.getEmail(), member.getPassword(), id);
    }


    public Integer remove(Integer id) {
        final var query = "DELETE FROM MEMBER WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }


    public void findSameProductExist(Member member) {
        final var query = "SELECT COUNT(*) FROM MEMBER WHERE email = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, member.getEmail(), member.getPassword());

        if (count > 0) {
            throw new IllegalArgumentException("같은 멤버가 존재합니다.");
        }
    }

    public Optional<Member> findById(Integer id) {
        final var query = "SELECT * FROM MEMBER WHERE id = ?";
        Member member = jdbcTemplate.queryForObject(query, Member.class, id);

        return Optional.of(member);
    }
}
