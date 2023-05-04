package cart.database.dao;

import cart.entity.ProductEntity;
import cart.validator.DefaultValidator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ProductEntity entity) {
        DefaultValidator.validate(entity);
        String sql = "INSERT INTO PRODUCT (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, entity.getName(), entity.getPrice(), entity.getImageUrl());
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new ProductEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price"))
        );
    }

    public void updateById(Long id, ProductEntity entity) {
        DefaultValidator.validate(entity);
        String sql = "UPDATE PRODUCT SET(name, price, image_url) = (?, ?, ?) WHERE id = ?";
        jdbcTemplate.update(sql, entity.getName(), entity.getPrice(), entity.getImageUrl(), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existById(Long productId) {
        String sql = "SELECT COUNT(*) FROM PRODUCT WHERE ID=?";
        Long count = Long.valueOf(jdbcTemplate.queryForObject(sql, Integer.class, productId));
        return count == 1;
    }
}
