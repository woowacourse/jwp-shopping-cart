package cart.dao;

import cart.entity.Product;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

        return jdbcTemplate.query(sql, (resultSet, count) ->
                new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("price")
                )
        );
    }

    @Override
    public void update(final Product product) {
        final String sql = "update product set name = :name, image_url = :imageUrl, price = :price where id = :id";
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void delete(final long id) {
        final String sql = "delete from product where id=?";
        jdbcTemplate.getJdbcOperations().update(sql, id);
    }
}
