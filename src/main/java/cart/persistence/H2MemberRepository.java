package cart.persistence;

import cart.business.domain.member.MemberId;
import cart.business.repository.MemberRepository;
import cart.business.domain.member.Member;
import cart.business.domain.member.MemberEmail;
import cart.business.domain.member.MemberPassword;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2MemberRepository implements MemberRepository {

    private final RowMapper<Member> memberRowMapper = (resultSet, rowNum) -> {
        Integer id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        return new Member(new MemberId(id), new MemberEmail(email), new MemberPassword(password));
    };

    private final JdbcTemplate jdbcTemplate;

    public H2MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        final String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Integer> findAndReturnId(Member member) {
        final String sql = "SELECT * FROM MEMBER WHERE email = (?) AND password = (?)";
        return jdbcTemplate.query(sql, memberRowMapper, member.getEmail(), member.getPassword())
                .stream()
                .map(Member::getId)
                .findAny();
    }

    @Override
    public Optional<Member> findById(Integer memberId) {
        final String sql = "SELECT * FROM MEMBER WHERE id = (?)";
        return jdbcTemplate.query(sql, memberRowMapper, memberId)
                .stream()
                .findAny();
    }
}
