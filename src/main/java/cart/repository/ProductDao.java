package cart.repository;

import static cart.repository.mapper.EntityRowMapper.productRowMapper;

import cart.domain.Product;
import cart.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public ProductEntity save(Product product) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);
        long productId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return ProductEntity.Builder.builder()
                .id(productId)
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT id, name, price, image_url FROM PRODUCT";
        return jdbcTemplate.query(sql, productRowMapper());
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
        String sql = "SELECT COUNT(*) FROM PRODUCT WHERE id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }
}
