package cart.entity.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DbProductDao implements ProductDao {
    private static final RowMapper<Product> productRowMapper = (resultSet, rowMapper) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("img_url"),
            resultSet.getInt("price")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public DbProductDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingColumns("name", "img_url", "price")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Product save(Product product) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Product(id, product.getName(), product.getImgUrl(), product.getPrice());
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Product findById(long id) {
        String sql = "SELECT * FROM product WHERE id = :id";
        Map<String, Long> parameter = Collections.singletonMap("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, parameter, productRowMapper);
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE product SET name = :name, img_url = :imgUrl, price = :price WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource(
                Map.of(
                        "name", product.getName(),
                        "imgUrl", product.getImgUrl(),
                        "price", product.getPrice(),
                        "id", product.getId()
                )
        );
        namedParameterJdbcTemplate.update(sql, parameters);

        return product;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM product WHERE id = :id";
        Map<String, Long> parameter = Collections.singletonMap("id", id);

        namedParameterJdbcTemplate.update(sql, parameter);
    }
}
