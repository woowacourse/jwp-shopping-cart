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
                new MemberCartEntity(rs.getLong("product_id"),
                        rs.getLong("member_id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getInt("price")), id);
    }

    public void save(CartEntity cartEntity) {
        String sql = "INSERT INTO carts (product_id, member_id) VALUES(?,?)";
        jdbcTemplate.update(sql, cartEntity.getProductId(), cartEntity.getMemberId());
    }

    public void delete(CartEntity cartEntity) {
        String sql = "DELETE FROM carts where product_id = ? and member_id = ?";
        jdbcTemplate.update(sql, cartEntity.getProductId(), cartEntity.getMemberId());
    }
}

