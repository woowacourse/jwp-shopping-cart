package cart.dao;

import cart.domain.Product;
import cart.entity.ProductEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {
    private static final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) ->
            ProductEntity.create(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("image"),
                    resultSet.getInt("price")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<ProductEntity> save(Product product) {
        Map<String, Object> parameterSource = new HashMap<>();

        parameterSource.put("name", product.getName());
        parameterSource.put("image", product.getImage());
        parameterSource.put("price", product.getPrice());
        parameterSource.put("created_at", Timestamp.valueOf(LocalDateTime.now()));

        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return findById(id);
    }

    @Override
    public ProductEntity update(ProductEntity entity) {
        String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getName(), entity.getImage(), entity.getPrice(), entity.getId());
        return entity;
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, productEntityRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
