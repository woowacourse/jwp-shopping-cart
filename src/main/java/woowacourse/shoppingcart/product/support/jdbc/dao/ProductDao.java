package woowacourse.shoppingcart.product.support.jdbc.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import woowacourse.shoppingcart.product.domain.Product;

@Repository
public class ProductDao {

    private static final RowMapper<Product> ROW_MAPPER =
            (resultSet, rowNum) -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")
            );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<Product> findById(final long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = (:id)";
            final SqlParameterSource parameters = new MapSqlParameterSource("id", productId);
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, parameters, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, ROW_MAPPER);
    }
}
