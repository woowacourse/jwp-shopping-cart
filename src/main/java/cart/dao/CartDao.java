package cart.dao;

import cart.entity.CartItem;
import cart.entity.Product;
import cart.entity.Product.Builder;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final RowMapper<CartItem> cartItemRowMapper = (resultSet, rowNum) -> {
        Product product = new Builder()
                .name(resultSet.getString("name"))
                .price(resultSet.getInt("price"))
                .imageUrl(resultSet.getString("image_url"))
                .build();
        return new CartItem.Builder()
                .id(resultSet.getInt("id"))
                .product(product)
                .build();
    };

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findAll(String memberEmail) {
        String sqlForFindAll = "SELECT c.id, p.name, p.price, p.image_url FROM Cart c JOIN Product p "
                + "WHERE c.product_id = p.id and c.member_email = ?";

        return jdbcTemplate.query(sqlForFindAll, cartItemRowMapper, memberEmail);
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

    public void deleteById(int cartId) {
        String sqlForDeleteById = "DELETE FROM Cart WHERE id = ?";
        jdbcTemplate.update(sqlForDeleteById, cartId);
    }

    public List<Integer> findCartIdByProductId(int productId, String memberEmail) {
        String sqlForFindByProductId = "SELECT id FROM Cart WHERE product_id = ? and member_email = ?";

        return jdbcTemplate.query(sqlForFindByProductId, (resultSet, rowNum) ->
                        resultSet.getInt("id"),
                productId, memberEmail);
    }

    public Optional<CartItem> findById(int cartId) {
        String sqlForFindById = "SELECT c.id, p.name, p.price, p.image_url FROM Cart c JOIN Product p "
                + "WHERE c.product_id = p.id and c.id = ?";
        List<CartItem> cartItems = jdbcTemplate.query(sqlForFindById, cartItemRowMapper, cartId);

        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(cartItems.get(0));
    }

}
