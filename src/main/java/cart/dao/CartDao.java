package cart.dao;

import cart.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(int memberId, int productId) {
        String sql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public List<ProductEntity> findAllByMemberId(int memberId) {
        String sql = "SELECT id, p.name, p.imgUrl, p.price\n"
                + "FROM cart AS c\n"
                + "INNER JOIN product AS p\n"
                + "ON c.product_id = p.id\n"
                + "WHERE member_id = ?;";
        BeanPropertyRowMapper<ProductEntity> mapper = BeanPropertyRowMapper.newInstance(ProductEntity.class);
        return jdbcTemplate.query(sql, mapper, memberId);
    }

    public void delete(int memberId, int productId) {
        String sql = "DELETE FROM cart WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }
}
