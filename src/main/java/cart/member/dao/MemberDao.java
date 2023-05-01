package cart.member.dao;

import cart.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberDao {

    private static final RowMapper<Member> memberRowMapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("phone_number")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MemberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Member save(Member member) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
        // 이메일에 중복을 허용하지 않는다는 검증을 어디서 해야할까요?
        // 도메인 로직이라고 생각이 드는데 도메인에서 그러면 매번 모든 객체를 꺼내야할까요?
        // 그냥 DAO에서 DupcliateKeyException이 발생하면 그걸 도메인에서 잡아서 예외 전환하는 방식을 택해야 할까요?
        // 하지만 이렇게 하면 service에서 jdbcTemplate이라는 기술에 대해 알아야 한다는 문제가 있을 거 같습니다.
        // 그렇다면 DAO에서 어떤 예외를 던져야 좋을까요?
        try {
            final Number key = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(member));
            return new Member(key.longValue(), member.getEmail(), member.getPassword(), member.getPhoneNumber());
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("키 중복 오류");
        }
    }

    public int update(Member member) {
        final String sql = "UPDATE members SET email = :email, password = :password, phone_number = :phoneNumber WHERE id = :id";

        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member));
    }

    public Optional<Member> findById(long id) {
        String sql = "SELECT id, email, password, phone_number FROM members WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            Member member = namedParameterJdbcTemplate.queryForObject(sql, params, memberRowMapper);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByEmailWithPassword(String email, String password) {
        String sql = "SELECT id, email, password, phone_number FROM members WHERE email = :email AND password = :password";
        final Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        MapSqlParameterSource params = new MapSqlParameterSource(map);

        try {
            Member member = namedParameterJdbcTemplate.queryForObject(sql, params, memberRowMapper);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Member> findAll() {
        final String sql = "SELECT id, email, password, phone_number FROM members";

        return namedParameterJdbcTemplate.query(sql, memberRowMapper);
    }

    public int deleteById(final long id) {
        final String sql = "DELETE FROM members WHERE id = :id";
        final MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.update(sql, params);
    }
}
