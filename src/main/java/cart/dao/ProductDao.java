package cart.dao;

import cart.controller.dto.NewProductDto;
import cart.controller.dto.ProductDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductDto> selectAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, getProductRowMapper());
    }

    private RowMapper<ProductDto> getProductRowMapper() {
        return (resultSet, rowNum) -> new ProductDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image")
        );
    }

    public int insert(final NewProductDto newProductDto) {
        final String sql = "INSERT INTO PRODUCT (name, price, image) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                newProductDto.getName(),
                newProductDto.getPrice(),
                newProductDto.getImage()
        );
    }
}
