package cart.dao;

import cart.dto.ProductDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductDto save(final ProductDto product) {
        final String sql = "INSERT INTO products(name, image, price) VALUES (?, ?, ?)";

        final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImage());
            preparedStatement.setInt(3, product.getPrice());

            return preparedStatement;
        }, generatedKeyHolder);

        product.setId(generatedKeyHolder.getKey().longValue());
        return product;
    }

    public List<ProductDto> findAll() {
        final String sql = "SELECT id, name, image, price FROM products";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProductDto(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
    }

    public ProductDto update(final ProductDto productDto) {
        final String sql = "UPDATE products SET id=?, name=?, image=?, price=?";

        jdbcTemplate.update(sql, productDto.getId(), productDto.getName(), productDto.getImage(), productDto.getPrice());
        return productDto;
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
