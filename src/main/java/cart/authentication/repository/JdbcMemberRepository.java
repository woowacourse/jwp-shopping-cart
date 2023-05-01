package cart.authentication.repository;

import cart.authentication.entity.Member;
import cart.authentication.exception.MemberPersistenceFailedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into MEMBER (email, password) values (:email, :password)";
        try {
            int updateCount = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(member));
            if (updateCount == 0) {
                throw new MemberPersistenceFailedException("Member가 정상적으로 저장되지 않았습니다.");
            }
            return member;
        } catch (DuplicateKeyException exception) {
            throw new MemberPersistenceFailedException("이미 등록된 email입니다.");
        }
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "select * from MEMBER where email = :email";
        try {
            return namedParameterJdbcTemplate.queryForObject(
                    sql,
                    Map.of("email", email),
                    (rs, rowNum) -> new Member(
                            rs.getString("email"),
                            rs.getString("password")
                    ));
        } catch (EmptyResultDataAccessException exception) {
            throw new MemberPersistenceFailedException("주어진 ID로 Member를 찾을 수 없습니다.");
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from MEMBER";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> new Member(
                rs.getString("email"),
                rs.getString("password")
        ));
    }
}
