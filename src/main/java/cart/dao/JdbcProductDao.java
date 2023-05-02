package cart.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.entity.Product;

@Repository
public class JdbcProductDao implements ProductDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productMapper
        = (resultSet, rowNum) -> new Product(
        resultSet.getInt("id"),
        resultSet.getString("name"),
        resultSet.getInt("price"),
        resultSet.getString("image")
    );

    @Autowired
    public JdbcProductDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertProduct(Product product) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    @Override
    public List<Product> selectAllProducts() {
        String sql = "select id, name, price, image from product";
        return jdbcTemplate.query(sql, productMapper);
    }

    @Override
    public int updateProduct(Product product) {
        String sql = "update product set name = ?, price =? , image = ?  where id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImage(),
            product.getId());
    }

    @Override
    public int deleteProduct(int productId) {
        String sql = "delete from product where id = ?";
        return jdbcTemplate.update(sql, productId);
    }

}
