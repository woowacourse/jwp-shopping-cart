package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.List;

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

    public ProductEntity findById(final long id) {
        final String sql = "select " + ALL_COLUMNS + " from Product where id = ?";

        return jdbcTemplate.queryForObject(sql, productRowMapper, id);
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
        final String sql = "delete Product where id = ?";

        jdbcTemplate.update(sql, id);
    }

    public boolean isExist(final long id) {
        final String sql = "select count(*) from Product where id = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }
}
