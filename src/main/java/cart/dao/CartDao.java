package cart.dao;

import cart.dao.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findProductsByMemberId(final int memberId) {
        final String query = "SELECT name, price, image " +
                "FROM cart " +
                "JOIN product ON cart.product_id = product.id " +
                "JOIN member ON cart.member_id = member.id " +
                "WHERE member_id = ?";
        List<ProductEntity> productEntities = jdbcTemplate.query(query, getProductRowMapper(), memberId);
        return productEntities;
    }

    private RowMapper<ProductEntity> getProductRowMapper() {
        return (resultSet, rowNum) -> new ProductEntity.Builder()
                .name(resultSet.getString("name"))
                .price(resultSet.getInt("price"))
                .image(resultSet.getString("image"))
                .build();
    }
}
