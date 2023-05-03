package cart.dao;

import cart.entity.CartItem;
import cart.entity.Product;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public int save(int productId, String memberEmail) {
        String sqlForSave = "INSERT INTO Cart (member_email, product_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        return jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlForSave, new String[]{"id"});
            ps.setString(1, memberEmail);
            ps.setInt(2, productId);
            return ps;
        }, keyHolder);
    }

    public List<Integer> findCartIdByProductId(int productId, String memberEmail) {
        String sqlForFindByProductId = "SELECT id FROM Cart WHERE product_id = ? and member_email = ?";

        return jdbcTemplate.query(sqlForFindByProductId, (resultSet, rowNum) ->
                        resultSet.getInt("id"),
                productId, memberEmail);
    }

}
