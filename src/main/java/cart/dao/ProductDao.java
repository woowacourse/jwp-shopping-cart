package cart.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("product_id");
    }

    public List<ProductEntity> findAll() {
        String findAllQuery = "SELECT * FROM product";

        return jdbcTemplate.query(findAllQuery, (rs, rowNum) ->
                new ProductEntity(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("category"),
                        rs.getString("image_url")
                ));
    }

    public Long insert(ProductEntity productEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);
        
        return simpleInsert.executeAndReturnKey(params).longValue();
    }

}
