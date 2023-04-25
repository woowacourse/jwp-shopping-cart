package cart.dao;

import cart.entity.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class H2ProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public H2ProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final Product product) {
        final String sql = "insert into product (name, image_url, price) values(:name, :imageUrl, :price)";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public List<Product> findAll() {
        final String sql = "select * from product";
        final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(final Product product) {
        final String sql = "update product set (name, image_url, price) values(:name, :imageUrl, :price) where id = :id";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void delete(final long id) {
        final String sql = "delete from product where id=?";
        jdbcTemplate.getJdbcOperations().update(sql, id);
    }
}
