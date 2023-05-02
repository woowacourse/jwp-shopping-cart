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
  public List<Member> findAll() {
    final String sql = "select * from member";
    return namedParameterjdbcTemplate.query(sql, getMemberRowMapper());
  }

  private static RowMapper<Member> getMemberRowMapper() {
    return (resultSet, count) ->
        new Member(resultSet.getString("email"), resultSet.getString("password"));
  }
}
