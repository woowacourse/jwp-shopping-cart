package cart.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.dao.entity.Product;

@Repository
public class JdbcProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final Product product) {
        final String sql = "INSERT INTO product(name, price, img_url) VALUES (:name, :price, :imgUrl)";
        final SqlParameterSource params = new BeanPropertySqlParameterSource(product);

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT id, name, price, img_url FROM product";

        final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM product WHERE id = :id";

        final Map<String, Long> params = Collections.singletonMap("id", id);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void update(Long id, Product product) {
        final String sql = "UPDATE product SET name = :name, price = :price, img_url = :imgUrl WHERE id = :id";

        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("imgUrl", product.getImgUrl());

        jdbcTemplate.update(sql, params);
    }
}
