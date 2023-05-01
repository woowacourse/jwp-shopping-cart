package cart.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao implements Dao<ProductEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    final RowMapper<ProductEntity> rowMapper = (rs, rowNum) ->
            new ProductEntity(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url")
            );

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PRODUCT")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ProductEntity findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl(),
                productEntity.getId()
        );
    }

    @Override
    public void save(final ProductEntity productEntity) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(productEntity));
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE from product where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
