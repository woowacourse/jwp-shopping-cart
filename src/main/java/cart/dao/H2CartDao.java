package cart.dao;

import cart.entity.CartEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartDao implements CartDao{

  private final SimpleJdbcInsert simpleJdbcInsert;
  private final NamedParameterJdbcTemplate namedParameterjdbcTemplate;

  public H2CartDao(final NamedParameterJdbcTemplate namedParameterjdbcTemplate) {
    this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterjdbcTemplate.getJdbcTemplate())
        .withTableName("cart")
        .usingGeneratedKeyColumns("id");
    this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
  }

  @Override
  public Long save(final CartEntity cartEntity) {
    final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartEntity);
    return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
  }

  @Override
  public List<CartEntity> findCartByMemberId(final long memberId) {
    final String sql = "select * from cart where member_id=?";
    return namedParameterjdbcTemplate.getJdbcTemplate().query(sql, (resultSet, count) ->
        new CartEntity(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("member_id"),
            resultSet.getInt("cart_count")
        ), memberId);
  }

  @Override
  public void deleteByMemberIdAndProductId(final long memberId, final long productId) {
    final String sql = "delete from cart where member_id=? and product_id=?";
    namedParameterjdbcTemplate.getJdbcOperations().update(sql, memberId, productId);
  }

  @Override
  public Optional<CartEntity> findCartByMemberIdAndProductId(long memberId, long productId) {
    try {
      final String sql = "select * from cart where member_id=? and product_id=?";
      final CartEntity cartEntity = namedParameterjdbcTemplate.getJdbcTemplate().queryForObject(sql, getRowMapper(), memberId, productId);
      return Optional.of(cartEntity);
    } catch (EmptyResultDataAccessException exception) {
      return Optional.empty();
    }
  }
}
