package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ProductEntity> productMapper
            = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    private final RowMapper<Boolean> booleanMapper = (resultSet, rowNum) -> resultSet.getBoolean("isExist");

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertProduct(ProductEntity productEntity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    public List<ProductEntity> selectAllProducts() {
        String sql = "select id, name, price, image from product";
        return jdbcTemplate.query(sql, productMapper);
    }

    public void updateProduct(ProductEntity productEntity) {
        String sql = "update product set name = ?, price =? , image = ?  where id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getPrice(), productEntity.getImage(), productEntity.getId());
    }

    public boolean isProductExist(int productId) {
        String sql = "select exists(" +
                "select * from product where product.id = ?) as isExist";
        return jdbcTemplate.queryForObject(sql, booleanMapper, productId);
    }

    public void deleteProduct(int productId) {
        String sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, productId);
    }

    public void deleteAllProduct() {
        String sql = "delete from product";
        jdbcTemplate.update(sql);
    }

}
