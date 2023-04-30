package cart.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PRODUCT")
                .usingGeneratedKeyColumns("id");
    }

    public void save(final ProductEntity productEntity) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(productEntity));
    }

    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";

        final RowMapper<ProductEntity> rowMapper = (rs, rowNum) ->
                new ProductEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                );

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void modify(final ProductEntity modifiedProductEntity) {
        final String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                modifiedProductEntity.getName(),
                modifiedProductEntity.getPrice(),
                modifiedProductEntity.getImageUrl(),
                modifiedProductEntity.getId()
        );
    }

    public void deleteById(final Long productId) {
        final String sql = "DELETE from product where id = ?";

        jdbcTemplate.update(sql, productId);
    }
}
