package cart.dao;

import cart.domain.cart.CartProduct;
import cart.domain.member.Member;
import cart.domain.product.ImageUrl;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.domain.product.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartProductJdbcDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            new Name(resultSet.getString("name")),
            new ImageUrl(resultSet.getString("image")),
            new Price(resultSet.getLong("price"))
    );

    public CartProductJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_product")
                .usingColumns("member_id", "product_id")
                .usingGeneratedKeyColumns("id");
    }

    public Long saveAndGetId(final CartProduct cartProduct) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(cartProduct);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Product> findAllProductByMember(final Member member) {
        final String sql = "SELECT product.id AS id, name, image, price "
                + "FROM cart_product "
                + "LEFT JOIN product "
                + "ON cart_product.product_id = product.id "
                + "WHERE member_id = ?";
        return jdbcTemplate.query(sql, productRowMapper, member.getId());
    }

    public int delete(final Long productId, final Member member) {
        final String sql = "DELETE FROM cart_product WHERE product_id = ? AND member_id = ?";
        return jdbcTemplate.update(sql, productId, member.getId());
    }
}
