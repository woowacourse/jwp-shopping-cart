package cart.dao;

import cart.domain.Product;
import cart.dto.request.ProductRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    private final SimpleJdbcInsert insertActor;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> {
        Product product = new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url"));
        return product;
    };

    public ProductDao(DataSource dataSource) {
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(ProductRequest productRequest) {
        String sql = "insert into Product(name, price, image_url) values (?,?,?)";

        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", productRequest.getName());
        parameters.put("price", productRequest.getPrice());
        parameters.put("image_url", productRequest.getImageUrl());

        insertActor.execute(parameters);
    }

    public List<Product> findAll() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(Long id) {
        String sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
