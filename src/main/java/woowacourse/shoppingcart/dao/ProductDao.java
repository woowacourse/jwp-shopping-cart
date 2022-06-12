package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private static final RowMapper<Product> ROW_MAPPER = (resultSet, rowNum) ->
            new Product(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Optional<Product> findById(Long id) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }
}
