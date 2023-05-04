package cart.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CartDao {

    private static final int EXITING_CART_ITEM = 1;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public CartDao(final DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addProduct(final int memberId, final int productId) throws DataIntegrityViolationException {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("member_id", memberId);
        params.addValue("product_id", productId);
        simpleJdbcInsert.executeAndReturnKey(params);
    }

    public boolean existingCartItem(final int memberId, final int productId) {
        final String sql = "select * from cart where member_id = ? and product_id = ?";
        final int size = jdbcTemplate.query(sql, (ig, ig2) -> null, memberId, productId).size();
        return size >= EXITING_CART_ITEM;
    }
}
