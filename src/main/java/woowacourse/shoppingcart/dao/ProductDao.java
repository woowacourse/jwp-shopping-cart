package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.product.Product;

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

    public Optional<Product> findProductById(final Long productId) {
        try {
            final String sql = "SELECT id, name, price, image FROM product WHERE id = :id";
            SqlParameterSource parameters = new MapSqlParameterSource("id", productId);
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, getProductMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, image FROM product";
        return jdbcTemplate.query(sql, getProductMapper());
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :id";
        SqlParameterSource parameters = new MapSqlParameterSource("id", productId);
        jdbcTemplate.update(query, parameters);
    }

    private RowMapper<Product> getProductMapper() {
        return (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image")
        );
    }
}
