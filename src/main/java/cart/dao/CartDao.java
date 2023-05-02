package cart.dao;

import cart.domain.product.Product;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findByUserId(final long userId) {
        final String sql = "SELECT product.name, product.price, product.image_url " +
                "FROM cart " +
                "JOIN product " +
                "ON cart.product_id = product.id " +
                "WHERE cart.user_id = :user_id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("user_id", userId);
        return jdbcTemplate.query(sql, sqlParameterSource, (rs, rowNum) -> Product.createWithoutId(
                rs.getString("product.name"),
                rs.getLong("product.price"),
                rs.getString("image_url")
        ));
    }
}
