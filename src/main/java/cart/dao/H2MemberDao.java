package cart.dao;

import cart.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    return (resultSet, count) -> new MemberEntity(resultSet.getLong("id"),
        resultSet.getString("email"),
        resultSet.getString("password"));
  }

  @Override
  public Optional<MemberEntity> findByMemberEntity(final MemberEntity memberEntity) {
    final String sql = "select * from member where email = :email and password = :password";
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(memberEntity);
    return namedParameterjdbcTemplate.query(sql, parameterSource, getMemberRowMapper())
        .stream()
        .findAny();
  }
}
