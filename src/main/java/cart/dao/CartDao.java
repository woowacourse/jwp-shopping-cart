package cart.dao;

import cart.domain.CartEntity;
import cart.domain.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartEntity> findAll(int memberId) {
        String sqlForFindAll = "SELECT c.id, p.name, p.price, p.image "
                + "FROM Carts c JOIN Products p "
                + "ON c.product_id = p.id "
                + "WHERE c.member_id = ?";

        return jdbcTemplate.query(sqlForFindAll, cartItemRowMapper, memberId);
    }

    private final RowMapper<CartEntity> cartItemRowMapper = (resultSet, rowNum) -> {
        ProductEntity product = new ProductEntity.Builder()
                .name(resultSet.getString("name"))
                .image(resultSet.getString("image"))
                .price(resultSet.getInt("price"))
                .build();
        return new CartEntity.Builder()
                .id(resultSet.getInt("id"))
                .product(product)
                .build();
    };

    public int insert(int memberId, int productId) {
        String sql = "INSERT INTO CARTS(member_Id, product_id) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"Id"});
            preparedStatement.setInt(1, memberId);
            preparedStatement.setInt(2, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void deleteById(int cartId) {
        String sqlForDeleteById = "DELETE FROM Carts WHERE id = ?";
        jdbcTemplate.update(sqlForDeleteById, cartId);
    }

}
