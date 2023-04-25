package cart.dao;

import cart.controller.dto.ProductDto;
import cart.controller.dto.ProductRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final ProductRequest productRequest) {
        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, productRequest.getName());
            ps.setInt(2, productRequest.getPrice());
            ps.setString(3, productRequest.getImageUrl());
        });
    }

    public List<ProductDto> findAll() {
        String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql, resultSet -> {
            List<ProductDto> products = new ArrayList<>();
            while (resultSet.next()) {
                ProductDto productDto = new ProductDto(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                products.add(productDto);
            }
            return products;
        });
    }

    public void updateById(final Long id, final ProductRequest productRequest) {
        String sql = "UPDATE product SET name = ?, image_url = ?, price = ? WHERE id = ?";

        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, productRequest.getName());
            ps.setString(2, productRequest.getImageUrl());
            ps.setInt(3, productRequest.getPrice());
            ps.setLong(4, id);
        });
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM product WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
