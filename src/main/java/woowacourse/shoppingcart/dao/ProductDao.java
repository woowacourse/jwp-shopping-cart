package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }


    public Product save(Product product) {
        long id = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(product))
                .longValue();
        return new Product(id, product.getName(), product.getPrice(), product.getImage());
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
//            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
//                    new Product(
//                            productId,
//                            resultSet.getString("name"), resultSet.getInt("price"),
//                            resultSet.getString("image_url")
//                    ), productId
//            );
            return null;
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("image_url")
                        ));
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        //jdbcTemplate.update(query, productId);
    }
}
