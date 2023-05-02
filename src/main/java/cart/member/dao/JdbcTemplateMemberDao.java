package cart.member.dao;

import cart.member.entity.MemberEntity;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
}
