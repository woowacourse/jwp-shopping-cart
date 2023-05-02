package cart.dao;

import cart.dto.entity.CartEntity;
import cart.dto.entity.MemberCartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberCartEntity> findCartByMember(Long id) {
        String sql = "SELECT * FROM carts c JOIN products p ON c.product_id = p.id WHERE c.member_id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new MemberCartEntity(
                        rs.getLong("product_id"),
                        rs.getLong("member_id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getInt("price")
                ), id
        );
    }

    public void save(Long productID, Long memberId) {
        String sql = "INSERT INTO carts (product_id, member_id) VALUES(?,?)";
        jdbcTemplate.update(sql, productID, memberId);
    }

    public void delete(CartEntity cartEntity) {
        String sql = "DELETE FROM carts where product_id = ? and member_id = ?";
        jdbcTemplate.update(sql, cartEntity.getProductId(), cartEntity.getMemberId());
    }

    public boolean existById(final Long id) {
        final String sql = "SELECT EXISTS( SELECT * FROM products WHERE id=? )";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}

