package cart.dao;

import cart.domain.Cart;
import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(long memberId, List<Long> productIds) {
        String cartProductsSql = "insert into CART (member_id, product_id) values (?, ?)";

        List<Object[]> products = productIds.stream()
                .map(id -> new Object[]{memberId, id})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(cartProductsSql, products);
    }

    public Cart findByMemberId(long memberId) {
        String cartSql = "select PRODUCT.product_id, name, image, price from CART NATURAL JOIN PRODUCT where CART.member_id = (?)";

        List<Product> products = jdbcTemplate.query(cartSql, (rs, rowNum) -> new Product(rs.getLong("product_id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getLong("price")), memberId);

        System.out.println(products);

        return new Cart(memberId, products);
    }

    public void insertProduct(long memberId, long productId) {
        String sql = "insert into CART (member_id, product_id) values (?,?)";

        jdbcTemplate.update(sql, memberId, productId);
    }

    public void deleteProduct(long memberId, long productId) {
        String sql = "delete from CART where member_id = ? AND product_id = ?";

        jdbcTemplate.update(sql, memberId, productId);
    }
}
