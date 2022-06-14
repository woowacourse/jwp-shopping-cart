package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.product.ProductNotFoundException;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")
            );

    public void save(final Product product) {
        SqlParameterSource namedParameterSource = new BeanPropertySqlParameterSource(product);
        simpleJdbcInsert.execute(namedParameterSource);
    }

    public Product getById(final long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, rowMapper, productId);
        } catch (final EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, rowMapper);
    }

    public void delete(final long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        int count = jdbcTemplate.update(query, productId);

        if (count == 0) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
    }
}
