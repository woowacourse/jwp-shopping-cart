package cart.dao;

import cart.controller.dto.NewProductDto;
import cart.controller.dto.ProductDto;
import cart.dao.entity.ProductEntity;
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

    public List<ProductEntity> selectAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, getProductRowMapper());
    }

    private RowMapper<ProductEntity> getProductRowMapper() {
        return (resultSet, rowNum) -> new ProductEntity(
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

    public void update(final ProductDto productDto) {
        final String sql = "UPDATE PRODUCT SET name = ?, price = ?, image = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImage(),
                productDto.getId()
        );
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
