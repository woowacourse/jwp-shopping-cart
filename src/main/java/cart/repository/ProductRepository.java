package cart.repository;

import cart.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public long save(ProductEntity productEntity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT id, name, price, image_url FROM PRODUCT";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    private RowMapper<ProductEntity> productRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong(1);
            String name = rs.getString(2);
            int price = rs.getInt(3);
            String imageUrl = rs.getString(4);
            return new ProductEntity(id, name, price, imageUrl);
        };
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(ProductEntity productEntity) {
        String sql = "UPDATE PRODUCT SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getPrice(), productEntity.getImageUrl(),
                productEntity.getId());
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM PRODUCT WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }
}
