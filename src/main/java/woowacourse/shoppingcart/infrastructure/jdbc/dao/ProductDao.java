package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    public static final RowMapper<Product> ROW_MAPPER =
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

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<Product> findProductById(final long id) {
        try {
            final String sql = "SELECT id, name, price, image_url FROM product WHERE id = (:id)";
            final SqlParameterSource parameters = new MapSqlParameterSource("id", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, ROW_MAPPER));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
