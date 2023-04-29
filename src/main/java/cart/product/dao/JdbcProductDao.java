package cart.product.dao;

import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
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
    
    public void update(final Product product) {
        final String sql = "update product_list set name = ?, image = ?, price = ? where id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, product.getName().getValue());
            preparedStatement.setString(2, product.getImage());
            preparedStatement.setInt(3, product.getPrice().getValue());
            preparedStatement.setLong(4, product.getId());
            return preparedStatement;
        });
    }
    
    @Override
    public long insert(final Product product) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName().getValue());
        parameters.put("image", product.getImage());
        parameters.put("price", product.getPrice().getValue());
        
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Product> findByID(final long id) {
        final String sql = "select * from product_list where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, productRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void deleteByID(final long id) {
        final String sql = "delete from product_list where id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public List<Product> findAll() {
        final String sql = "select * from product_list";
        return jdbcTemplate.query(sql, productRowMapper);
    }
}
