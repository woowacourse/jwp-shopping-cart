package woowacourse.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.member.domain.Member;
import woowacourse.member.domain.SavedPassword;
import woowacourse.member.exception.MemberNotFoundException;

import javax.sql.DataSource;

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

    private RowMapper<Member> rowMapper = (resultSet, rowNum) ->
            new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("name"),
                    new SavedPassword(resultSet.getString("password"))
            );

    public Member getByEmail(String email) {
        try {
            String SQL = "SELECT id, email, name, password FROM member WHERE email = ?";
            return jdbcTemplate.queryForObject(SQL, rowMapper, email);
        } catch (final EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("해당 이메일로 가입된 회원은 존재하지 않습니다.");
        }
    }

    public boolean existsByEmail(String email) {
        String SQL = "SELECT EXISTS (SELECT * FROM member WHERE email = ?)";
        return jdbcTemplate.queryForObject(SQL, Boolean.class, email);
    }

    public void save(Member member) {
        SqlParameterSource namedParameterSource = new BeanPropertySqlParameterSource(member);
        simpleJdbcInsert.execute(namedParameterSource);
    }

    public Member getById(long id) {
        try {
            String SQL = "SELECT id, email, name, password FROM member WHERE id = ?";
            return jdbcTemplate.queryForObject(SQL, rowMapper, id);
        } catch (final EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    public void updateNameById(long id, String name) {
        String SQL = "UPDATE member SET name = ? WHERE id = ?";
        jdbcTemplate.update(SQL, name, id);
    }

    public void updatePasswordById(long id, String password) {
        String SQL = "UPDATE member SET password = ? WHERE id = ?";
        jdbcTemplate.update(SQL, password, id);
    }

    public void deleteById(long id) {
        String SQL = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}
