package cart.dao;

import cart.entity.CartEntity;
import java.util.List;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartDao implements CartDao{

  private final SimpleJdbcInsert simpleJdbcInsert;
  private final NamedParameterJdbcTemplate namedParameterjdbcTemplate;

  public H2CartDao(NamedParameterJdbcTemplate namedParameterjdbcTemplate) {
    this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterjdbcTemplate.getJdbcTemplate())
        .withTableName("cart")
        .usingGeneratedKeyColumns("id");
    this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
  }

  @Override
  public Long save(CartEntity cartEntity) {
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartEntity);
    return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
  }

  @Override
  public List<CartEntity> findCartByMemberId(long memberId) {
    final String sql = "select * from cart where member_id=?";
    return namedParameterjdbcTemplate.getJdbcTemplate().query(sql, (resultSet, count) ->
        new CartEntity(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("member_id"),
            resultSet.getInt("cart_count")
        ), memberId);
  }
}
