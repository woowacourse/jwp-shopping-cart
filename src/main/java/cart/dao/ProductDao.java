package cart.dao;

import cart.domain.Product;
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

    public Long save(Product product) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("image_url", product.getImageUrl());

        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    public List<Product> findAll() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteById(Long id) {
        String sql = "delete from product where id = ?";
        int count = jdbcTemplate.update(sql, id);
        hasNoMatchingResult(count);
    }

    private void hasNoMatchingResult(int count) {
        if (count == 0) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public void updateById(Long id, Product product) {
        String sql = "update product " +
                "set name = ? , price = ?, image_url = ? " +
                "where id = ?";
        int count = jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
        hasNoMatchingResult(count);
    }
}
