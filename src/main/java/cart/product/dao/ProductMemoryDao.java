package cart.product.dao;

import cart.product.domain.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ProductMemoryDao implements ProductDao {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Product> rowMapper;
    
    public ProductMemoryDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.simpleJdbcInsert = initSimpleJdbcInsert(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = initRowMapper();
    }
    
    private SimpleJdbcInsert initSimpleJdbcInsert(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }
    
    private RowMapper<Product> initRowMapper() {
        return (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price")
        );
    }
    
    @Override
    public Product findById(final Long id) {
        final String sql = "SELECT * FROM PRODUCT WHERE id=:id";
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
    }
    
    @Override
    public List<Product> findAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }
    
    @Override
    public Long save(final Product product) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", product.getName().getName())
                .addValue("imageUrl", product.getImageUrl().getUrl())
                .addValue("price", product.getPrice().getPrice());
        
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }
    
    @Override
    public void update(final Product product) {
        String sql = "UPDATE PRODUCT SET name=:name.name,image_url=:imageUrl.url, price=:price.price WHERE id=:id";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
    
    @Override
    public void delete(final Long id) {
        final String sql = "DELETE FROM PRODUCT WHERE id=:id";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
