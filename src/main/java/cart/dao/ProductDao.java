package cart.dao;

import cart.domain.product.Product;
import cart.domain.product.ProductEntity;
import cart.domain.product.ProductId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<ProductEntity> productEntityRowMapper() {
        return (resultSet, rowNum) -> new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    }

    public long insert(final Product product) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public ProductEntity find(final ProductId id) {
        final String sql = "select id, name, price, image_url from Product where id = ?";

        return jdbcTemplate.queryForObject(sql, productEntityRowMapper(), id.getValue());
    }

    public List<ProductEntity> findAll() {
        final String sql = "select id, name, price, image_url from Product";

        return jdbcTemplate.query(sql, productEntityRowMapper());
    }

    public void update(final ProductEntity newProduct) {
        final String sql = "update Product set name = ?, price = ?, image_url = ? where id = ?";

        jdbcTemplate.update(
                sql,
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getImageUrl(),
                newProduct.getId()
        );
    }

    public void delete(final ProductId id) {
        final String sql = "delete Product where id = ?";

        jdbcTemplate.update(sql, id.getValue());
    }

    public boolean isExist(final ProductId id) {
        final String sql = "select count(*) from Product where id = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, id.getValue()) > 0;
    }
}
