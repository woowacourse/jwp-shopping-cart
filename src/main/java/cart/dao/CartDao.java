package cart.dao;

import cart.entity.CartItem;
import cart.entity.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findAll(String memberEmail) {
        String sqlForFindAll = "SELECT c.id, p.name, p.price, p.image_url FROM Cart c JOIN Product p "
                + "WHERE c.product_id = p.id and c.member_email = ?";

        return jdbcTemplate.query(sqlForFindAll, (resultSet, rowNum) -> {
            Product product = new Product.Builder()
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .imageUrl(resultSet.getString("image_url"))
                    .build();
            return new CartItem(resultSet.getInt("id"), product);
        }, memberEmail);
    }

}
