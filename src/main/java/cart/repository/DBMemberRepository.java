package cart.repository;

import cart.entity.MemberEntity;
import cart.exception.DuplicateEmailException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DBMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MemberEntity save(MemberEntity memberEntity) {
        String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, memberEntity.getEmail());
            preparedStatement.setString(2, memberEntity.getPassword());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new MemberEntity(id, memberEntity.getEmail(), memberEntity.getPassword());
    }

    @Override
    public Optional<MemberEntity> findById(Long id) {
        String sql = "SELECT id, email, password FROM member WHERE id = ?";
        List<MemberEntity> entities = jdbcTemplate.query(sql, memberEntityMaker(), id);
        return entities.stream().findAny();
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) throws DuplicateEmailException {
        String sql = "SELECT id, email, password FROM member WHERE email = ?";
        List<MemberEntity> entities = jdbcTemplate.query(sql, memberEntityMaker(), email);
        if (entities.size() > 1) {
            throw new DuplicateEmailException("동일한 이메일이 2개 이상 존재합니다.");
        }
        return entities.stream().findAny();
    }

    private RowMapper<MemberEntity> memberEntityMaker() {
        return (rs, rowNum) -> new MemberEntity(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"));
    }

    @Override
    public List<MemberEntity> findAll() {
        String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql, memberEntityMaker());
    }

    @Override
    public void update(MemberEntity memberEntity) {
        String sql = "UPDATE member SET email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                memberEntity.getEmail(),
                memberEntity.getPassword(),
                memberEntity.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
