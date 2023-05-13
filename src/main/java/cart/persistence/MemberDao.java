package cart.persistence;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM MEMBER";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int id = resultSet.getInt("id");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    return new MemberEntity(id, email, password);
                });
    }

    public Integer update(Integer id, MemberEntity member) {
        String sql = "UPDATE MEMBER SET email = ?, password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, member.getEmail(), member.getPassword(), id);
    }


    public Integer remove(Integer id) {
        final var query = "DELETE FROM MEMBER WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }

    public Optional<MemberEntity> findByEmail(String email) {
        final var query = "SELECT * FROM MEMBER WHERE email = ?";
        MemberEntity member = jdbcTemplate.queryForObject(query, getMemberRowMapper(), email);

        return Optional.of(member);
    }

    private RowMapper<MemberEntity> getMemberRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String mail = resultSet.getString("email");
            String password = resultSet.getString("password");

            return new MemberEntity(id, mail, password);
        };
    }
}
