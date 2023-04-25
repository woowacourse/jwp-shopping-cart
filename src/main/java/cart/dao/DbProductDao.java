package cart.dao;

import cart.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DbProductDao implements ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DbProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product (name, img_url, price) VALUES (:name, :img_url, :price)";

        String name = product.getName();
        String imgUrl = product.getImgUrl();
        Integer price = product.getPrice();

        SqlParameterSource parameters = new MapSqlParameterSource(
                Map.of(
                        "name", name,
                        "img_url", imgUrl,
                        "price", price
                )
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Product(id, name, imgUrl, price);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, (resultSet, rowMapper) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("img_url"),
                resultSet.getInt("price")
        ));
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
