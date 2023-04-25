package cart.product.dao;

import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductDao implements ProductDao {
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    
    private final RowMapper<Product> productRowMapper = (resultSet, rowNumber) -> {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String image = resultSet.getString("image");
        int price = resultSet.getInt("price");
        
        return new Product(id, new Name(name), image, new Price(price));
    };
    
    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product_list")
                .usingGeneratedKeyColumns("id");
    }
    
    
    @Override
    public long insert(final Product product) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName().getValue());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice().getValue());
        
        return this.simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }
    
    @Override
    public Product findByID(final long id) {
        final String sql = "select * from product_list where id = ?";
        return this.jdbcTemplate.queryForObject(sql, this.productRowMapper, id);
    }
    
    @Override
    public List<Product> findAll() {
        final String sql = "select * from product_list";
        return this.jdbcTemplate.query(sql, this.productRowMapper);
    }
}
