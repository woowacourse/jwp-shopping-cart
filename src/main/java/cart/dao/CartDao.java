package cart.dao;

import cart.domain.Cart;
import cart.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image"),
            resultSet.getLong("price"),
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getTimestamp("updated_at").toLocalDateTime()
    );

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingColumns("member_id", "product_id")
                .usingGeneratedKeyColumns("id");
    }

    public Long saveAndGetId(final Cart cart) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(cart);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Product> findAllProductByMemberId(final Long memberId) {
        final String sql = "SELECT product.id AS id, name, image, price, product.created_at, product.updated_at "
                + "FROM cart "
                + "LEFT JOIN product "
                + "ON cart.product_id = product.id "
                + "WHERE member_id = ?";
        return jdbcTemplate.query(sql, productRowMapper, memberId);
    }
}
