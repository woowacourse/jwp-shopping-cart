package cart.persistence.dao;

import cart.persistence.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductDao implements Dao<ProductEntity> {

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
    public Optional<ProductEntity> findById(final Long id) {
        final String sql = "SELECT product_id, name, price, image_url FROM product WHERE product_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, actorRowMapper, id));
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT product_id, name, price, image_url FROM product";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int update(final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name=?, price=?, image_url=? WHERE product_id = ?";
        return jdbcTemplate.update(sql, productEntity.getName(), productEntity.getPrice(), productEntity.getImageUrl(), productEntity.getId());
    }

    @Override
    public int deleteById(final long id) {
        final String sql = "DELETE FROM product WHERE product_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<ProductEntity> findProductsByUser(final String email) {
        final String sql = "SELECT p.product_id, p.name, p.price, p.image_url\n" +
                "FROM product AS p\n" +
                "JOIN cart AS c ON p.product_id = c.product_id\n" +
                "WHERE c.user_id = (\n" +
                "    SELECT user_id\n" +
                "    FROM user_info\n" +
                "    WHERE email = ?)";
        return jdbcTemplate.query(sql, actorRowMapper, email);
    }

    public Long findProductIdByName(String name) {
        final String sql = "SELECT product_id FROM user_info WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, name);
    }
}
