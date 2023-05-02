package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartDao implements CartDao{

  private final SimpleJdbcInsert simpleJdbcInsert;

  public H2CartDao(JdbcTemplate jdbcTemplate) {
    this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("cart")
        .usingGeneratedKeyColumns("id");
  }

  @Override
  public Long save(CartEntity cartEntity) {
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartEntity);
    return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
  }
}
