package cart.dao;

import cart.entity.ProductCategoryEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductCategoryDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product_category")
            .usingColumns("product_id", "category_id")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final ProductCategoryEntity productCategoryEntity) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productCategoryEntity);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<ProductCategoryEntity> findAll(final Long productId) {
        final String sql = "SELECT * FROM product_category WHERE product_id = ?";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new ProductCategoryEntity(
                rs.getLong("product_id"),
                rs.getLong("category_id")),
            productId
        );
    }
}
