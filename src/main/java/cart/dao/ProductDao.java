package cart.dao;

import cart.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productMapper
            = (resultSet, rowNum) -> new Product(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(Product product) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    public List<Product> selectAll() {
        String sql = "select id, name, price, image from product";
        return jdbcTemplate.query(sql, productMapper);
    }

    public int update(Product product) {
        String sql = "update product set name = ?, price =? , image = ?  where id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImage(),
                product.getId());
    }

    public int delete(int productId) {
        String sql = "delete from product where id = ?";
        return jdbcTemplate.update(sql, productId);
    }

}
