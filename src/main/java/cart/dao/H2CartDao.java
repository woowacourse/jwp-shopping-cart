package cart.dao;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
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
    public List<ProductEntity> findProductsByMemberId(final Long id) {
        final String sql = "SELECT product.* from cart LEFT JOIN product on cart.product_id = product.id "
                + "where cart.member_id = ?";
        return jdbcTemplate.query(sql, (resultSet, count) -> new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")), id);
    }
}
