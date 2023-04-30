package cart.member.dao;

import cart.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
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

        final Number key = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(member));
        return new Member(key.longValue(), member.getEmail(), member.getPassword(), member.getPhoneNumber());
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
