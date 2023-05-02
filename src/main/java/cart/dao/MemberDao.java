package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
<<<<<<< HEAD
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
=======
import org.springframework.stereotype.Component;

import java.util.List;
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)

@Component
public class MemberDao {

<<<<<<< HEAD
    private static final RowMapper<MemberEntity> rowMapper =
            (rs, rowNum) -> new MemberEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3)
            );

=======
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(final MemberEntity entity) {
<<<<<<< HEAD
        String sql = "INSERT INTO MEMBER (email, password) VALUES (?, ?)";
=======
        String sql = "INSERT INTO MEMEBER (email, password) VALUES (?, ?)";
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
        jdbcTemplate.update(sql, entity.getEmail(), entity.getPassword());
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, (rs, rowNum)
                        -> new MemberEntity(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password")
                )
        );
    }

<<<<<<< HEAD
    public Optional<MemberEntity> findByEmail(String email) {
        String sql = "SELECT * FROM MEMBER WHERE email = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, email));
    }

=======
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
}
