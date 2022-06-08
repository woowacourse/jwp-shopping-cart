package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ProductDao(DataSource dataSource,
                      NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        return insertActor.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("imageUrl", product.getImageUrl())
                .addValue("quantity", product.getQuantity())
        ).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT * FROM product WHERE id = :id";
            // 주의
            Map<String, Long> params = Map.of("id", productId);

            return jdbcTemplate.queryForObject(query, params, rowMapper());
        } catch (final EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT * FROM product";
        return jdbcTemplate.query(query, rowMapper());
    }
    public void deleteById(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", productId);

        jdbcTemplate.update(query, namedParameters);
    }
    private RowMapper<Product> rowMapper() {
        return (rs, rowNum) ->
                new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url"),
                        rs.getLong("quantity")
                );
    }
}
