package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ProductDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        final BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(product);
        return simpleInsert.executeAndReturnKey(parameter).longValue();
    }

    public Product findProductById(final Long productId) {
        final String query = "SELECT name, price, image_url, description, stock FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                new Product(
                        productId,
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url"),
                        resultSet.getString("description"),
                        resultSet.getInt("stock")
                ), productId
        );
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url, description, stock FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("image_url"),
                                resultSet.getString("description"),
                                resultSet.getInt("stock")
                        ));
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
