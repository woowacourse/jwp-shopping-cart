package cart.dao.member;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public MemberEntity save(final MemberEntity member) {
        String sql = "insert into member(email, name, phone_number, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getName(), member.getPhoneNumber(), member.getPassword());
        return member;
    }

    @Override
    public List<MemberEntity> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, mapRow());
    }

    @Override
    public MemberEntity findByEmail(final String email) {
        String sql = "select * from member where email = ?";
        return jdbcTemplate.queryForObject(sql, mapRow(), email);
    }

    private RowMapper<MemberEntity> mapRow() {
        return (rs, rowNum) -> {
            String email = rs.getString(1);
            String name = rs.getString(2);
            String phoneNumber = rs.getString(3);
            String password = rs.getString(4);
            return new MemberEntity(email, name, phoneNumber, password);
        };
    }

    @Override
    public void update(final MemberEntity item) {
        String sql = "update member set name = ?, phone_number = ?, password = ? where email = ?";
        jdbcTemplate.update(sql, item.getName(), item.getPhoneNumber(), item.getPassword(), item.getEmail());
    }

    @Override
    public void delete(final String email) {
        String sql = "delete from member where email = ?";
        jdbcTemplate.update(sql, email);
    }
}
