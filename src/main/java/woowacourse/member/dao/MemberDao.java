package woowacourse.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.password.ExistPassword;
import woowacourse.member.exception.MemberNotFoundException;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Member member) {
        SqlParameterSource namedParameterSource = new BeanPropertySqlParameterSource(member);
        simpleJdbcInsert.execute(namedParameterSource);
    }

    public boolean existMemberByEmail(String email) {
        String SQL = "SELECT EXISTS (SELECT * FROM MEMBER WHERE email = ?)";
        return jdbcTemplate.queryForObject(SQL, Boolean.class, email);
    }

    public Optional<Member> findMemberByEmail(String email) {
        try {
            String SQL = "SELECT id, email, name, password FROM member WHERE email = ?";
            Member member = jdbcTemplate.queryForObject(SQL, rowMapper, email);
            return Optional.ofNullable(member);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findMemberById(long id) {
        try {
            String SQL = "SELECT id, email, name, password FROM member WHERE id = ?";
            Member member = jdbcTemplate.queryForObject(SQL, rowMapper, id);
            return Optional.ofNullable(member);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) ->
            new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    new ExistPassword(resultSet.getString("password"))
            );

    public void updateName(long id, String name) {
        String SQL = "UPDATE member SET name = ? WHERE id = ?";
        jdbcTemplate.update(SQL, name, id);
    }

    public void updatePassword(long id, String password) {
        String SQL = "UPDATE member SET password = ? WHERE id = ?";
        jdbcTemplate.update(SQL, password, id);
    }

    public int deleteById(long id) {
        String SQL = "DELETE FROM member WHERE id = ?";
        return jdbcTemplate.update(SQL, id);
    }

    // 기존 제공 코드로 2단계 shopping cart를 진행하며 제거할 예정
    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM member WHERE name = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }
    }
}
