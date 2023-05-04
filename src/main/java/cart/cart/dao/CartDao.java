package cart.cart.dao;

import cart.cart.entity.Cart;
import cart.member.entity.Member;
import cart.product.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNum) -> {
        Cart cart = new Cart(
                resultSet.getLong("id"),
                new Product(
                        resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("image"),
                        resultSet.getLong("price"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                ),
                new Member(
                        resultSet.getLong("member_id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"))
        );
        return cart;
    };

    public void insertCart(Long productId, Long memberId) {
        String sql = "INSERT INTO cart (product_id,member_id) VALUES (?,?)";
        jdbcTemplate.update(sql, productId, memberId);
    }

    public List<Cart> findCartsByMemberId(Long memberId) {
        String sql = "SELECT * FROM cart join product on product.id = product_id join member on member.id = member_id where member.id = ?";
        return jdbcTemplate.query(sql, cartRowMapper, memberId);
    }

//    public void deleteCartByMemberIdAndProductId(Long memberId, Long productId) {
//        String sql = "DELETE FROM cart WHERE member_id = ? and product_id = ?";
//        jdbcTemplate.update(sql, memberId, productId);
//    }

    public void deleteCartByCartId(Long cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }
}
