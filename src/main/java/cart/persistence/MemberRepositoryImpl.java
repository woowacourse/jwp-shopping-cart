package cart.persistence;

import cart.domain.Member;
import cart.domain.MemberRepository;
import cart.dto.LoginDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Long memberId = rs.getLong("member_id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        return new Member(memberId, email, password);
    };

    @Override
    public List<Member> findAll() {
        String sql = "SELECT member_id, email, password FROM member";

        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public boolean contains(Member member) {
        String sql = "SELECT COUNT(*) FROM member WHERE email = :email AND password = :password";

        var parameterSource = new MapSqlParameterSource("email", member.getEmail())
                .addValue("password", member.getPassword());

        Integer count = jdbcTemplate.queryForObject(sql, parameterSource, int.class);
        return count > 0;
    }

    @Override
    public Optional<Member> findByEmailAndPassword(LoginDto loginDto) {
        String sql = "SELECT member_id, email, password FROM member WHERE email = :email AND password = :password";

        var parameterSource = new MapSqlParameterSource("email", loginDto.getEmail())
                .addValue("password", loginDto.getPassword());

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource, memberRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
