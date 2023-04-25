package cart.dao;

import cart.entity.ProductEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateProductDao implements ProductDao{

    private final SimpleJdbcInsert insertProducts;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateProductDao(JdbcTemplate jdbcTemplate) {
        this.insertProducts = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(final ProductEntity product) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("image", product.getImage());
        insertProducts.execute(parameters);
    }

    @Override
    public List<ProductEntity> selectAll() {
        String sql = "select * from products";

        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public void update(final ProductEntity product) {
        String sql = "update products set (name, price, image) = (?, ?, ?) where id = ?";

        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImage(), product.getId());
    }

    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNumber) -> {
        ProductEntity productEntity = new ProductEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image")
        );
        return productEntity;
    };
}
