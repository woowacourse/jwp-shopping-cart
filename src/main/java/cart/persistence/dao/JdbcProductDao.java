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
        resultSet.getBytes("image"),
        resultSet.getInt("price")
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
        return null;
    }

    @Override
    public List<ProductEntity> findAll() {
        String sql = "SELECT product_id, name, image, price FROM PRODUCT";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public void update(final ProductEntity productEntity) {

    }

    @Override
    public void deleteByName(final String name) {

    }
}
