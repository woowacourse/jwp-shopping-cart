package cart.dao;

import cart.controller.dto.CartResponse;
import cart.entity.CartEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartDao implements CartDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public H2CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final CartEntity cartEntity) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
                cartEntity);
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public List<CartResponse> findProductsByMemberId(final Long id) {
        final String sql = "SELECT cart.id AS id, product.name AS name, product.image_url AS image_url, product.price AS price "
                        + "FROM cart LEFT JOIN product ON cart.product_id = product.id "
                        + "WHERE cart.member_id = ?";
        return jdbcTemplate.query(sql, (resultSet, count) -> new CartResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")), id);
    }

    @Override
    public void deleteById(final Long cartId) {
        final String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }
}
