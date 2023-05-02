package cart.dao;

import cart.entity.Product;
import java.util.List;

import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
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
                                .price(Price.of(resultSet.getInt("price")))
                                .name(Name.of(resultSet.getString("name")))
                                .imageUrl(Url.of(resultSet.getString("image_url")))
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
