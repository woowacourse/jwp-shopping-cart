package cart.dao;

import cart.dao.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<MemberEntity> rowMapper = (resultSet, rowNum) ->
            new MemberEntity.Builder()
                    .id(resultSet.getInt("id"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .build();

    @Autowired
    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String query = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(query, rowMapper);
    }

    public Long findIdByAuthInfo(final String email, final String password) {
        final String query = "SELECT id FROM MEMBER WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(query, Long.class, email, password);
    }
}
