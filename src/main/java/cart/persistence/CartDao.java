package cart.persistence;

import cart.entity.Cart;
import cart.entity.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer insert(Cart cart) {
        String sql = "INSERT INTO CART (member_id) VALUES(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, cart.getMemberId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<Cart> findAll() {
        String sql = "SELECT * FROM CART";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int id = resultSet.getInt("id");
                    int memberId = resultSet.getInt("member_id");

                    return new Cart(id, memberId);
                });
    }

    public List<Product> findAllProductsByMemberId(Integer memberId) {
        //todo: 쿼리문 질문
        String query = "SELECT p.* FROM Cart c JOIN Product p ON c.product_id = p.id WHERE c.member_id = ?";
        return jdbcTemplate.query(query, new Object[]{memberId}, new BeanPropertyRowMapper<>(Product.class));
    }

    public void findSameCartExist(Cart cart) {
        final var query = "SELECT COUNT(*) FROM CART WHERE member_id = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, cart.getMemberId());

        if (count > 0) {
            throw new IllegalArgumentException("장바구니가 이미 존재합니다.");
        }
    }

    public Integer remove(Integer memberId) {
        final var query = "DELETE FROM CART WHERE member_id = ?";
        return jdbcTemplate.update(query, memberId);
    }

    public Optional<Cart> findByMemberId(Integer memberId) {
        final var query = "SELECT * FROM CART WHERE member_id = ?";
        Cart cart = jdbcTemplate.queryForObject(query, Cart.class, memberId);

        return Optional.of(cart);
    }
}
