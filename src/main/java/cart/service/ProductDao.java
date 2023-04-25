package cart.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
