package cart.dao;

import cart.entity.CartEntity;
import cart.exception.InternalServerException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartDao implements CartDao {

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
    return namedParameterjdbcTemplate.getJdbcTemplate().query(sql, getRowMapper(), memberId);
  }

  private RowMapper<CartEntity> getRowMapper() {
    return (resultSet, count) ->
        new CartEntity(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("member_id"),
            resultSet.getInt("cart_count")
        );
  }

  @Override
  public void deleteByMemberIdAndProductId(final long memberId, final long productId) {
    try {
      final String sql = "delete from cart where member_id=? and product_id=?";
      namedParameterjdbcTemplate.getJdbcOperations().update(sql, memberId, productId);
    } catch (DataIntegrityViolationException exception) {
      throw new InternalServerException("제약조건 때문에 삭제할 수 없습니다.");
    } catch (EmptyResultDataAccessException exception) {
      throw new InternalServerException("카트가 존재하지 않습니다.");
    }
  }

  @Override
  public Optional<CartEntity> findCartByMemberIdAndProductId(final long memberId, final long productId) {
    final String sql = "select * from cart where member_id=? and product_id=?";
    return namedParameterjdbcTemplate.getJdbcTemplate()
        .query(sql, getRowMapper(), memberId, productId)
        .stream()
        .findAny();
  }

  @Override
  public void addCartCount(final int count, final long memberId, final long productId) {
    final String sql = "update cart set cart_count = ? where member_id = ? and product_id = ?";
    namedParameterjdbcTemplate.getJdbcTemplate().update(sql, count, memberId, productId);
  }
}
