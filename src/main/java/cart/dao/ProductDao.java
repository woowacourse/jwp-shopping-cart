package cart.dao;

import cart.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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

    public long insert(final Product product) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(product);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public Product findById(final long id) {
        final String sql = "select id, name, price, image_url from Product where id = ?";

        return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
    }

    public List<Product> findAll() {
        final String sql = "select id, name, price, image_url from Product";

        return jdbcTemplate.query(sql, productRowMapper());
    }

    public void update(final Product newProduct) {
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

    private RowMapper<Product> productRowMapper() {
        return (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    }
}
