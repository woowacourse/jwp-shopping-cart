package cart.dao;

import cart.domain.Product;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    public List<Product> findAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price")
        ));
    }
    
    public long save(final Product product) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }
    
    public void update(final Product product) {
        String sql = "UPDATE PRODUCT SET NAME=:name,IMAGE_URL=:imageUrl, PRICE=:price WHERE ID=:id";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
    
    public void delete(final Long id) {
        final String sql = "DELETE FROM PRODUCT WHERE ID=:id";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
