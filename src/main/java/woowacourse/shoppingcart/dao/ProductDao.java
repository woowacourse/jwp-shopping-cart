package woowacourse.shoppingcart.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NoSuchProductException;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Product product) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public Product findProductById(Long productId) {
        try {
            String sql = "SELECT id, name, price, image_url, selling FROM product WHERE id = :id";
            SqlParameterSource parameterSource = new MapSqlParameterSource("id", productId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, mapToProduct());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchProductException();
        }
    }

    private RowMapper<Product> mapToProduct() {
        return (resultSet, rowNum) ->
                new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url"),
                        resultSet.getBoolean("selling"));
    }

    public List<Product> findAllProducts() {
        String sql = "SELECT id, name, price, image_url, selling FROM product";
        return namedParameterJdbcTemplate.query(sql, mapToProduct());
    }

    public List<Product> findSellingProducts() {
        String sql = "SELECT id, name, price, image_url, selling FROM product WHERE selling = true";
        return namedParameterJdbcTemplate.query(sql, mapToProduct());
    }

    public void delete(Long productId) {
        String sql = "UPDATE product SET selling = false WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", productId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }
}
