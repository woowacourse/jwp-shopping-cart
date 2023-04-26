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

    public List<Product> selectAll() {
        String sqlForSelectAll = "SELECT * FROM Product";
        return jdbcTemplate.query(
                sqlForSelectAll,
                (resultSet, rowNum) ->
                        new Product.Builder().id(resultSet.getInt("id"))
                                .price(resultSet.getInt("price"))
                                .name(resultSet.getString("name"))
                                .imageUrl(resultSet.getString("image_url"))
                                .build()
        );
    }

    public void save(Product product) {
        String sqlForSave = "INSERT INTO Product (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlForSave, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void deleteById(int id) {
        String sqlForDeleteById = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sqlForDeleteById, id);
    }

    public void updateById(int id, Product product) {
        String sqlForUpdateById = "UPDATE Product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sqlForUpdateById, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

}
