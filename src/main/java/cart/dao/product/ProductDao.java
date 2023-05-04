package cart.dao.product;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {
    private static final RowMapper<ProductEntity> rowMapper =
            (rs, rowNum) -> new ProductEntity(
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("category"),
                    rs.getString("image_url")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("product_id");
    }

    public Long insert(ProductEntity productEntity) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public List<ProductEntity> findAll() {
        String findAllQuery = "SELECT * FROM product";

        return jdbcTemplate.query(findAllQuery, rowMapper);
    }

    public void deleteById(Long id) {
        String deleteByIdQuery = "DELETE FROM product where product_id = ?";

        jdbcTemplate.update(deleteByIdQuery, id);
    }

    public int update(ProductEntity productEntity) {
        String updateProductQuery = "UPDATE product SET name = ?, price = ?, category = ?, image_url =? WHERE product_id = ?";

        int countOfUpdate = jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(updateProductQuery);

            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setString(3, productEntity.getCategory());
            preparedStatement.setString(4, productEntity.getImageUrl());
            preparedStatement.setLong(5, productEntity.getId());
            return preparedStatement;
        });

        return countOfUpdate;
    }

    public Optional<ProductEntity> findById(Long id) {
        String findByIdQuery = "SELECT * FROM product WHERE product_id = ?";

        try {
            ProductEntity productEntity = jdbcTemplate.queryForObject(findByIdQuery, rowMapper, id);

            return Optional.of(productEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ProductEntity> findByIds(List<Long> ids) {
        String findByIdQuery = "SELECT * FROM product WHERE product_id IN (%s)";

        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        return jdbcTemplate.query(String.format(findByIdQuery, inSql),
                ids.toArray(), rowMapper);
    }
}
