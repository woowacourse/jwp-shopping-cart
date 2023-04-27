package cart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("product_id");
    }

    public List<ProductEntity> findAll() {
        String findAllQuery = "SELECT * FROM product";

        return jdbcTemplate.query(findAllQuery, (rs, rowNum) ->
                new ProductEntity(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("category"),
                        rs.getString("image_url")
                ));
    }

    public Long insert(ProductEntity productEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM product where product_id = ?";

        jdbcTemplate.update(deleteByIdQuery, id);
    }

    public ProductEntity update(ProductEntity productEntity) {
        String updateProductQuery = "UPDATE product SET name = ?, price = ?, category = ?, image_url =? WHERE product_id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(updateProductQuery);

            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setString(3, productEntity.getCategory());
            preparedStatement.setString(4, productEntity.getImageUrl());
            preparedStatement.setLong(5, productEntity.getId());
            return preparedStatement;
        });

        return productEntity;
    }
}
