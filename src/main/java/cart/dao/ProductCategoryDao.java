package cart.dao;

import cart.entity.ProductCategoryEntity;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductCategoryDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductCategoryEntity> findAll(final Long productId) {
        final String sql = "SELECT id, product_id, category_id FROM product_category WHERE product_id = ?";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ProductCategoryEntity(
                        rs.getLong("id"),
                        rs.getLong("product_id"),
                        rs.getLong("category_id")
                ),
                productId
        );
    }

    public int saveAll(final List<ProductCategoryEntity> productCategoryEntities) {
        final String sql = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
        final int[] rowsAffected = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, productCategoryEntities.get(i).getProductId());
                ps.setLong(2, productCategoryEntities.get(i).getCategoryId());
            }

            @Override
            public int getBatchSize() {
                return productCategoryEntities.size();
            }
        });
        return Arrays.stream(rowsAffected).sum();
    }

    public void deleteAllByProductId(final Long productId) {
        final String sql = "DELETE FROM product_category where product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
