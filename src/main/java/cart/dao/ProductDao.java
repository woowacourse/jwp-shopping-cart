package cart.dao;

import cart.entity.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new Product.Builder().id(resultSet.getInt("id"))
                                .price(resultSet.getInt("price"))
                                .name(resultSet.getString("name"))
                                .imageUrl(resultSet.getString("image_url"))
                                .build()
        );
    }

    public void save(Product product) {
        String sql = "INSERT INTO Product (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateById(int id, Product product) {
        String sql = "UPDATE Product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }
}
