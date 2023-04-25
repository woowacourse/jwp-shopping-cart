package cart.dao;

import cart.controller.dto.ProductDto;
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
}
