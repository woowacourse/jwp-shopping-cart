package cart.dao;

import cart.global.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao implements Dao<ProductEntity> {

    final RowMapper<ProductEntity> rowMapper = (rs, rowNum) ->
            new ProductEntity(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url")
            );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PRODUCT")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ProductEntity findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        ProductEntity productEntity = jdbcTemplate.queryForObject(sql, rowMapper, id);

        if (productEntity == null) {
            throw new ProductNotFoundException();
        }

        return productEntity;
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void save(final ProductEntity productEntity) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(productEntity));
    }

    @Override
    public int update(final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";

        return jdbcTemplate.update(
                sql,
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl(),
                productEntity.getId()
        );
    }

    @Override
    public int deleteById(final Long id) {
        final String sql = "DELETE from product where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
