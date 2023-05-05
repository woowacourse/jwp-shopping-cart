package cart.dao;

import cart.domain.CartProduct;
import cart.domain.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartProductDao {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<CartProduct> rowMapper;
    private final RowMapper<Product> productRowMapper;

    public CartProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.simpleJdbcInsert = initSimpleJdbcInsert(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = initRowMapper();
        this.productRowMapper = initProductRowMapper();
    }

    private SimpleJdbcInsert initSimpleJdbcInsert(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<CartProduct> initRowMapper() {
        return (rs, rowNum) -> new CartProduct(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("product_id")
        );
    }

    private RowMapper<Product> initProductRowMapper() {
        return (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price")
        );
    }

    public long save(final CartProduct cartProduct) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(cartProduct);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<Product> findAllProductByMemberId(final long memberId) {

        final String sql = "SELECT product.id, name, image_url, price "
                + "FROM cart_product "
                + "LEFT JOIN product "
                + "ON cart_product.product_id = product.id "
                + "WHERE member_id=:memberId";

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("memberId", memberId);
        final List<Product> products = namedParameterJdbcTemplate.query(sql, sqlParameterSource, productRowMapper);
        return products;
    }

    public void delete(final Long memberId, final Long productId) {
        final String sql = "DELETE FROM CART_PRODUCT WHERE MEMBER_ID=:memberId AND PRODUCT_ID=:productId";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("productId", productId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
