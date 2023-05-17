package cart.persistence;

import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAllProductsByMemberId(Integer memberId) {
        String query = "SELECT p.* " +
                "FROM CART c " +
                "JOIN CART_PRODUCT cp ON c.id = cp.cart_id " +
                "JOIN PRODUCT p ON p.id = cp.product_id " +
                "WHERE c.member_id = ?";

        return jdbcTemplate.query(query, (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String url = resultSet.getString("url");
            int price = resultSet.getInt("price");

            return new ProductEntity(id, name, url, price);
        }, memberId);
    }

    public Integer remove(Integer memberId) {
        final var query = "DELETE FROM CART WHERE member_id = ?";
        return jdbcTemplate.update(query, memberId);
    }
}
