package cart.dao;

import cart.auth.MemeberDto;
import cart.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemeberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> {
        Member member = new Member(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return member;
    };

    public MemeberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member findByEmail(String email) {
        String sql = "select * from member where email = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, email);
    }

    public void save(MemeberDto memeberDto) {
        String sql = "insert into member(email, password) values (?, ?)";
        jdbcTemplate.update(sql, memeberDto.getEmail(), memeberDto.getPassowrd());
    }

    public List<Member> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, rowMapper);
    }

}
