package cart.dao;

import cart.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2ProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) ->
            new ProductEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("price")
            );

    @Override
    public Long save(Product product) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public ProductEntity update(ProductEntity entity) {
        String sql = "UPDATE product SET name = ?, image_url = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getName(), entity.getImageUrl(), entity.getPrice(), entity.getId());
        return entity;
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        return jdbcTemplate.query(sql, productEntityRowMapper, id).stream()
                .findAny();
    }

    @Override
    public List<ProductEntity> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
