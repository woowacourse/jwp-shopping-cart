package cart.dao;

import cart.domain.Cart;
import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Cart cart) {
        String cartProductsSql = "insert into CART (member_id, product_id) values (?, ?)";

        List<Object[]> products = cart.getProducts().stream()
                .map(product -> new Object[]{cart.getMemberId(), product.getId()})
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

        // TODO: 2023-05-03 질문 어떻게 효율적으로 쿼리해오나 cart, cart_product가 구조 효율적인가/확장성 있나
        return new Cart(memberId, products);
    }

    void insertProduct(long memberId, long productId) {
        String sql = "insert into CART (member_id, product_id) values (?,?)";

        jdbcTemplate.update(sql, memberId, productId);
    }

    void deleteProduct(long memberId, long productId) {
        String sql = "delete from CART where member_id = ? AND product_id = ?";

        jdbcTemplate.update(sql, memberId, productId);
    }
}
