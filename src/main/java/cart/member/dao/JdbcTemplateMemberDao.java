package cart.member.dao;

import cart.member.entity.MemberEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMember;

    public JdbcTemplateMemberDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertMember = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<MemberEntity> selectAll() {
        String sql = "select * from members";

        return jdbcTemplate.query(sql, memberEntityRowMapper);
    }

    private final RowMapper<MemberEntity> memberEntityRowMapper = (resultSet, rowNumber) -> {
        MemberEntity memberEntity = new MemberEntity(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return memberEntity;
    };

    @Override
    public Optional<MemberEntity> findByEmail(final String memberEmail) {
        String sql = "select * from members where email = ?";

        return jdbcTemplate.query(sql, memberEntityRowMapper, memberEmail).stream()
                .findAny();
    }

    @Override
    public MemberEntity insert(final MemberEntity member) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("password", member.getPassword());
        insertMember.execute(parameters);

        final Optional<MemberEntity> savedMember = findByEmail(member.getEmail());

        return savedMember.get();
    }
}
