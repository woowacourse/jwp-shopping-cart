package cart.dao;

import cart.entity.Member;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H2MemberDao implements MemberDao {

  private final NamedParameterJdbcTemplate namedParameterjdbcTemplate;

  public H2MemberDao(NamedParameterJdbcTemplate namedParameterjdbcTemplate) {
    this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
  }

  @Override
  public List<MemberEntity> findAll() {
    final String sql = "select * from member";
    return namedParameterjdbcTemplate.query(sql, getMemberRowMapper());
  }

  private RowMapper<MemberEntity> getMemberRowMapper() {
    return (resultSet, count) ->
        new MemberEntity(resultSet.getString("email"), resultSet.getString("password"));
  }
}
