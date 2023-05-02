package cart.dao;

import cart.entity.CartEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("CART")
            .usingColumns("customer_id", "product_id")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final CartEntity cartEntity) {
        final Number savedId = simpleJdbcInsert.executeAndReturnKey(
            new BeanPropertySqlParameterSource(cartEntity));
        return savedId.longValue();
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM CART WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteByCustomerIdAndProductId(final Long customerID, final Long productId) {
        final String sql = "DELETE FROM CART WHERE customer_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, customerID, productId);
    }

    public List<Long> findAllProductIdsBy(final Long customerId) {
        final String sql = "SELECT product_id FROM CART WHERE customer_id=?";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> rs.getLong("product_id"),
            customerId
        );
    }

    public List<Long> findIdsBy(final Long customerId, final Long productId) {
        final String sql = "SELECT (id) FROM CART WHERE customer_id = ? AND product_id = ?";
        final List<Long> cartIds = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> rs.getLong("id"),
            customerId,
            productId
        );
        return cartIds;
    }
}
