package cart.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
}
