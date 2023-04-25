package cart.dao;

import cart.entity.Product;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.webresources.AbstractArchiveResource;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) ->
        new Product.Builder().id(resultSet.getInt("id"))
                .price(resultSet.getInt("price"))
                .name(resultSet.getString("name"))
                .imageUrl(resultSet.getString("image_url"))
                .build();

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void save(Product product) {
        String sql = "INSERT INTO Product (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
