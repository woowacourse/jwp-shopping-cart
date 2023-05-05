package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {
    private static final int ITEM_NOT_FOUND = 0;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ProductEntity> productEntityRowMapper = (rs, rn) ->
            new ProductEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getInt("price")
            );

    public ProductDao(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<Object> nullMapper() {
        return (rs, cn) -> null;
    }

    public int create(ProductEntity productEntity) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(productEntity);
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    public List<ProductEntity> findAll() {
        final String sql = "select * from product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    public void update(ProductEntity productEntity) {
        final String updateSql = "update product set name = ?, image = ?, price = ? where id = ?";
        jdbcTemplate.update(updateSql,
                productEntity.getName(),
                productEntity.getImage(),
                productEntity.getPrice(),
                productEntity.getId()
        );
    }

    public void delete(final int id) {
        final String sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean exitingProduct(final int id) {
        final String sql = "select * from product where id = ?";
        final List<Object> findItems = jdbcTemplate.query(sql, nullMapper(), id);
        return findItems.size() != ITEM_NOT_FOUND;
    }

    public boolean exitingProductName(final String name) {
        final String sql = "select * from product where name = ?";
        final List<Object> findItems = jdbcTemplate.query(sql, nullMapper(), name);
        return findItems.size() != ITEM_NOT_FOUND;
    }

    public Optional<ProductEntity> findBy(final int id) {
        final String sql = "select * from product where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, productEntityRowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
