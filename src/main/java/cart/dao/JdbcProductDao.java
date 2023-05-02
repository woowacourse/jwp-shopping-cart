package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ProductEntity> productMapper
            = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    public JdbcProductDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertProduct(ProductEntity productEntity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(productEntity);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    @Override
    public List<ProductEntity> selectAllProducts() {
        String sql = "select id, name, price, image from product";
        return jdbcTemplate.query(sql, productMapper);
    }

    @Override
    public void updateProduct(ProductEntity productEntity) {
        String sql = "update product set name = ?, price =? , image = ?  where id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getPrice(), productEntity.getImage(), productEntity.getId());
    }

    @Override
    public void deleteProduct(int productId) {
        String sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, productId);
    }

    @Override
    public void deleteAllProduct() {
        String sql = "delete from product";
        jdbcTemplate.update(sql);
    }
}
