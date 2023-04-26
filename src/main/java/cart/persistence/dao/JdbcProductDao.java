package cart.persistence.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.persistence.entity.ProductEntity;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ProductEntity> actorRowMapper = (resultSet, rowNum) -> new ProductEntity(
        resultSet.getLong("product_id"),
        resultSet.getString("name"),
        resultSet.getInt("price"),
        resultSet.getString("image_url")
    );

    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("PRODUCT")
            .usingGeneratedKeyColumns("product_id");
    }

    @Override
    public Long save(final ProductEntity productEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public ProductEntity findByName(final String name) {
        final String sql = "SELECT product_id, name, price, image_url FROM product WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, name);
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT product_id, name, price, image_url FROM product";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public void update(final Long id, final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name=?, price=?, image_url=? WHERE product_id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getPrice(), productEntity.getImageUrl(), id);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM product WHERE product_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
