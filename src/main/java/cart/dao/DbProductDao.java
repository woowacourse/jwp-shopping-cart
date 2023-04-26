package cart.dao;

import cart.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DbProductDao implements ProductDao {
    public static final RowMapper<Product> productRowMapper = (resultSet, rowMapper) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("img_url"),
            resultSet.getInt("price")
    );

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DbProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product (name, img_url, price) VALUES (:name, :imgUrl, :price)";

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
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

        SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
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
