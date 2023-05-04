package cart.dao.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ProductDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("product_id");
    }

    public List<ProductEntity> findAll() {
        String findAllQuery = "SELECT * FROM product";

        return jdbcTemplate.query(findAllQuery, (rs, rowNum) -> toProductEntity(rs));
    }

    public int countById(Long id) {
        String findAllQuery = "SELECT count(*) FROM product WHERE product_id = ?";

        return jdbcTemplate.queryForObject(findAllQuery, Integer.class, id);
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

    public ProductEntity findById(Long id) {
        String findByProductQuery = "SELECT * FROM product WHERE product_id = ?";

        return jdbcTemplate.queryForObject(findByProductQuery, (rs, rowNum) -> toProductEntity(rs), id);
    }

    public List<ProductEntity> findById(List<Long> ids) {
        String findProducts = "SELECT * FROM product WHERE product_id IN (:productIds)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("productIds", ids);

        return namedParameterJdbcTemplate.query(findProducts, parameters, (rs, rowNum) -> toProductEntity(rs));
    }

    private ProductEntity toProductEntity(ResultSet resultSet) throws SQLException {
        return new ProductEntity(
                resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("category"),
                resultSet.getString("image_url")
        );
    }
}
