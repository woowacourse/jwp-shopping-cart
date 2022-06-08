package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("price")
            );

    public ProductDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("PRODUCT")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Product product) {
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public Product findProductById(Long productId) {
        try {
            final String query = "SELECT * FROM PRODUCT WHERE id = ?";
            return jdbcTemplate.queryForObject(query, productRowMapper, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM PRODUCT";
        return jdbcTemplate.query(query, productRowMapper);
    }

    public void delete(Long productId) {
        final String query = "DELETE FROM PRODUCT WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }

    public boolean existById(Long productId) {
        String query = "SELECT EXISTS (SELECT id FROM PRODUCT WHERE id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, productId);
    }
}
