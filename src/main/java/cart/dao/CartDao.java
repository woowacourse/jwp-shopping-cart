package cart.dao;

import cart.dao.joinrequest.CartWithProduct;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public void deleteProduct(final int memberId, final int productId) {
        final String sql = "delete from cart where member_id = ? and product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public boolean existingCartItem(final int memberId, final int productId) {
        final String sql = "select * from cart where member_id = ? and product_id = ?";
        final int size = jdbcTemplate.query(sql, (ig, ig2) -> null, memberId, productId).size();
        return size >= EXITING_CART_ITEM;
    }

    public List<CartWithProduct> cartWithProducts(final int memberId) {
        final String sql = "select cart.member_id, product.id, product.name, product.image, product.price" +
                " from cart inner join product"
                + " on cart.product_id = product.id"
                + " where cart.member_id = ?";

        return jdbcTemplate.query(sql,
                (rs, cn) -> new CartWithProduct(
                        rs.getInt("cart.member_id"),
                        rs.getInt("product.id"),
                        rs.getString("product.name"),
                        rs.getString("product.image"),
                        rs.getInt("product.price")
                ), memberId
        );
    }
}
