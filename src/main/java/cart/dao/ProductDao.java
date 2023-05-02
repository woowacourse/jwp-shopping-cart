package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {

    private static final String ALL_COLUMNS = "id, name, price, image_url";
    private static final RowMapper<ProductEntity> productRowMapper = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url")
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public long insert(final ProductEntity productEntity) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(productEntity);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public Optional<ProductEntity> findById(final long id) {
        final String sql = "select " + ALL_COLUMNS + " from Product where id = ?";

        try {
            final ProductEntity productEntity = jdbcTemplate.queryForObject(sql, productRowMapper, id);
            return Optional.ofNullable(productEntity);
        } catch (final DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ProductEntity> findAll() {
        final String sql = "select " + ALL_COLUMNS + " from Product";

        return jdbcTemplate.query(sql, productRowMapper);
    }

    public void update(final ProductEntity newProduct) {
        final String sql = "update Product set name = ?, price = ?, image_url = ? where id = ?";

        jdbcTemplate.update(sql,
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getImageUrl(),
                newProduct.getId()
        );
    }

    public void delete(final long id) {
        final String sql = "DELETE FROM Product WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
